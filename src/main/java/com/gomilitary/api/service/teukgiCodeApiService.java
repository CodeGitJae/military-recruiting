package com.gomilitary.api.service;

import java.io.BufferedInputStream;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gomilitary.api.entity.teukgiCodeEntity;
import com.gomilitary.api.repository.teukgiCodeApiRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class teukgiCodeApiService {

	private final teukgiCodeApiRepository teukgiCocdApiRepository;

    @Value("${data.portal.authentication.encoded}")
    private String encodedKey;
     
	public List<teukgiCodeEntity> getSavedData() {
//		System.out.println(teukgiCocdApiRepository.findAll());	
		return teukgiCocdApiRepository.findAll();
	}


	// API 데이터 호출 메서드
    public void getApiData() {
        try {
	        StringBuilder url = new StringBuilder("https://apis.data.go.kr/1300000/MJBGJWJeopSuHH4/list?"); /*URL*/
	        
	        url.append("serviceKey="+encodedKey);
	        url.append("&numOfRows="+URLEncoder.encode("1000", "UTF-8"));
	        url.append("&pageNo="+URLEncoder.encode("1", "UTF-8"));
	        
	        System.out.println("==========url print=========="+url.toString());
	
			getInfo(url.toString());
        }
		catch(Exception e) {
			e.printStackTrace();
		}
    }

	// api 데이터 파싱 및 DB 저장하는 메소드
    public void getInfo(String url) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();			
			con.connect();

			BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(bis));
			StringBuffer st = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				st.append(line);
			}

			// xml 데이터를 json type으로 변환 후 문자열로 출력
			JSONObject JSONObj = XML.toJSONObject(st.toString());
			
			// JSON 객체안에 데이터 까지 접근을 위한 코드
			JSONObject responseObj = JSONObj.getJSONObject("response");
			JSONObject bodyObj = responseObj.getJSONObject("body");
			JSONObject itemsObj = bodyObj.getJSONObject("items");
			JSONArray jsonArray = itemsObj.getJSONArray("item");
//			System.out.println(jsonArray);
			
			// 데이터를 저장할 변수 선언
			Double teukgiCode = (double) 0;
			String teukgiCodeStr = "";
			teukgiCodeEntity saveData = new teukgiCodeEntity();

			// jsonArray 에서 json object를 받아서 DB에 저장
			for(int i=0; i<jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
//				System.out.println("#########Object: " + object);

				// gsteukgiCd는 소수점이 있는 숫자이므로 Double로 읽음
				Object teukgiCodeObj = object.get("gsteukgiCd");
				if (teukgiCodeObj instanceof String) {
					teukgiCodeStr = (String) teukgiCodeObj;
//					System.out.println("#########teukgiCode as String: " + teukgiCodeStr);	
				} else if (teukgiCodeObj instanceof Number) {
					teukgiCode = ((Number) teukgiCodeObj).doubleValue();
//					System.out.println("#########teukgiCode as Number: " + teukgiCode);
				}

				String gunGubun = (String) object.get("gunGbnm");
				String teukgiName = (String) object.get("gsteukgiNm");

//				System.out.println("#########gunGubun: " +gunGubun +
//								"#########teukgiName: " +teukgiName);

				// 기존 데이터 존재 여부와 중복 데이터 각각 체크
				boolean existsCodeNum = teukgiCocdApiRepository.existsByTeukgiCode(teukgiCode);
//				System.out.println("existsCodeNum:::::::::::::::::::::"+existsCodeNum);
				boolean existsCodeStr =  teukgiCocdApiRepository.existsByTeukgiCodeStr(teukgiCodeStr);
//				System.out.println("existsCodeStr:::::::::::::::::::::"+existsCodeStr);
				
				// object type이 Number의 경우는 문자열 테이블 빈값으로 저장
				if(teukgiCodeObj instanceof Number && !existsCodeNum) {
					System.out.println("number: successfully created");
					saveData = new teukgiCodeEntity(null, teukgiCode, null, gunGubun, teukgiName);
					teukgiCocdApiRepository.save(saveData);
//					System.out.println("#########saveData: " + saveData);
				} else if(teukgiCodeObj instanceof String && !existsCodeStr) {
					// object type이 String의 경우는 숫자 테이블 빈값으로 저장
					System.out.println("string: successfully created");
					saveData = new teukgiCodeEntity(null, null, teukgiCodeStr, gunGubun, teukgiName);
					teukgiCocdApiRepository.save(saveData);
//					System.out.println("#########saveData: " + saveData);
				}				
			}
//        System.out.println("###################xmlJSONObj###################"+xmlJSONObj);
//	        String jsonPrettyPrintString = xmlJSONObj.toString(4);
//	        System.out.println(jsonPrettyPrintString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}
