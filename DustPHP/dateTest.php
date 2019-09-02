
<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $nowTime = date("Y-m-d", time());


//  $startDate = date("Y-m-d 00:00:01",$today);
//  $endDate = date("Y-m-d 23:59:59",$today);
  $num =0;

//  $result = mysqli_query($con, "SELECT * from DateCheck where dataTime >= '$startDate' AND dataTime <= '$endDate';"); //
//  var_dump ( $result);
//  if(mysqli_stmt_fetch($result)){ // 오늘이 지나지 않음
    //동작 x
//  }else{

      $statement = mysqli_prepare($con, "INSERT INTO DateCheck VALUES (?, ?)"); //
      mysqli_stmt_bind_param($statement, "is", $num, $nowTime);
      mysqli_stmt_execute($statement);

//  }
//  $result = mysqli_query('TRUNCATE TABLE DataCheck');

/*
  $response = array();
  $response["success"] = true;

  echo json_encode($response);
*/

?>
$현재 = time();
$시작 = date("Y-m-d 00:00:01",$today);
$끝 = date("Y-m-d 23:59:59",$today);


db에서 검색 select * from asdf where input_date >= '$시작' AND input_date <= '$끝'
검색결과있으면 스탑
없으면
\insert 함과 동시에 실행
