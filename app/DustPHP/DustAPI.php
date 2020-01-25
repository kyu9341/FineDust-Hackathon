<?php
$url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?serviceKey=vlg%2BdzlBn6db3PbzT7%2Fv00dNoocv1h83Y9O%2F9NFBcLgVPxkzEJwz%2FXDqeJkqMxRrvsPodSUgVVH1KTJ5zBQHuw%3D%3D&numOfRows=25&pageNo=1&sidoName=%EC%84%9C%EC%9A%B8&searchCondition=HOUR";
$ch = cURL_init();

cURL_setopt($ch, CURLOPT_URL, $url);
cURL_setopt($ch, CURLOPT_RETURNTRANSFER, 1);




$response = cURL_exec($ch);
cURL_close($ch);

$object = simplexml_load_string($response);
$suggest0 = $object->CompleteSuggestion[0]->suggestion["data"];

echo $suggest0;

?>
