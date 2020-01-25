<?php
  header("Content-Type: text/html; charset=UTF-8");
  $conn = mysqli_connect(    "localhost",    "root",    "1234",    "my_test_dust"  );
  $url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?serviceKey=gLUki0nkksO%2Frmi1YcLeAV%2FyFZNfniI6aCjr4m8myNf1Hvj8huuftj0BYURmACeyiM9zfCBh1HHCtsDX4XnIhg%3D%3D&numOfRows=25&pageNo=1&sidoName=%EC%84%9C%EC%9A%B8&searchCondition=DAILY";
  $ch = curl_init();
  curl_setopt($ch, CURLOPT_URL, $url);
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
  curl_setopt($ch, CURLOPT_HEADER, false);
  curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'GET');
  curl_setopt($ch, CURLOPT_USERAGENT, $agent); // 헤더항목 추가
  $response = curl_exec($ch);
  $object = simplexml_load_string($response);
  curl_close($ch);
  //var_dump($object);
  $num = 0;

  for($i = 0; $i<25; $i++){
    $name[$i]=$object->body->items->item[$i]->cityName;
    $dust[$i]=$object->body->items->item[$i]->pm10Value;
    $ult_dust[$i]=$object->body->items->item[$i]->pm25Value;
//    echo $name[$i];
//    echo $dust[$i];
//    echo $ult_dust[$i];
  }

  $statement = mysqli_prepare($con, "INSERT INTO DustData VALUES (?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "ssii", $dataTime[$i], $cityName[$i], $pm10Value[$i], $pm25Value[$i]);
  mysqli_stmt_execute($statement);


    $statement = mysqli_prepare($conn, "INSERT INTO test_dust VALUES (?, ?, ?, ?)");
    mysqli_stat_bind_param($statement, "isii", 0, $name[$i], $dust[$i], $ult_dust[$i]);
    mysqli_stat_execute($statment);


  for($i=0; $i<25; $i++){
      $statement = mysqli_prepare($conn, "INSERT INTO test_dust VALUES (?, ?, ?, ?)");
      mysqli_stat_bind_param($statement, "isii", $num, $name[$i], $dust[$i], $ult_dust[$i]);
      mysqli_stat_execute($statment);
    }

//  echo $statement;
//  var_dump($statement);


?>
