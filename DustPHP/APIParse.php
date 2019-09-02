<?php
header("Content-Type: text/html; charset=UTF-8");


function http_request($url) {

    $info = parse_url($url);
    $host = $info["host"];
    $port = $info["port"];

    if ($port == 0) $port = 80;
    $path = $info["path"];

    if ($info["query"] != "") $path .= "?" . $info["query"];
    $out = "GET $path HTTP/1.0\r\nHost: $host\r\n\r\n";

    $fp = fsockopen($host, $port, $errno, $errstr, 30);

    if(!$fp){
        echo "$errstr ($errno) <br>\n";
    } else {
        fputs($fp, $out);
        $start = false;
        $retVal = "";

        while(!feof($fp)) {
            $tmp = fgets($fp, 1024);
            if ($start == true) $retVal .= $tmp;
        if ($tmp == "\r\n") $start = true;
    }

        fclose($fp);
    }
    return simplexml_load_string($retVal);
}



$url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?serviceKey=vlg%2BdzlBn6db3PbzT7%2Fv00dNoocv1h83Y9O%2F9NFBcLgVPxkzEJwz%2FXDqeJkqMxRrvsPodSUgVVH1KTJ5zBQHuw%3D%3D&numOfRows=25&pageNo=1&sidoName=%EC%84%9C%EC%9A%B8&searchCondition=HOUR";
//$url .= "?searchSe=road";// searchSe ==>  road : 도로명, post : 우편번호 , dong 구주소
//$url .= "&srchwrd=중앙로 1";
//$url .= "&serviceKey=인증키";

//$data = file_get_contents($url);
//$xml = simplexml_load_string($data);


$xml = http_request($url);// 도로명주소 검색 API 정보 추출
//$xml = http_request($url);// 도로명주소 검색 API 정보 추출

echo $xml;
?>
