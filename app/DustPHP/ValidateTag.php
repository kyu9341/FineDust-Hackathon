<?php
$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

header("Content-Type:text/html;charset=utf-8");


$CardNum = $_POST["CardNum"];
//$CardNum = '34532453245';
$num = 0;
//$ID = 'afas';
$nowTime = date("Y-m-d", time());
//$nowTime = "2019-06-26";
$response = array();


//////

$resultCard = mysqli_query($con, "SELECT ID FROM Dust_user WHERE CardNum = '$CardNum';");
if($resultCard->num_rows >=1){

//  var_dump($resultCard);

  $row = mysqli_fetch_array($resultCard);
//  var_dump($row[0]);
//var_dump($row);

  $ID = $row[0];
  $response["existUser"] = true; // 존재하는 유저




//////

$result1 = mysqli_query($con, "SELECT * FROM DustTagID WHERE ID = '$ID';");


if($result1->num_rows >= 1) {

  $response["Validate"] = false; // 오늘 사용한 아이디일 경우

}else {

    $result = mysqli_prepare($con, "INSERT INTO DustTagID VALUES (?, ?, ?)");
    mysqli_stmt_bind_param($result, "iss", $num, $ID, $nowTime);
    mysqli_stmt_execute($result);

  $response["Validate"] = true; // 오늘 사용하지 않은 아이디일 경우
//  $response["ID"] = $ID;

}

}//if($resultCard->num_rows >=1){// end
else{
  $response["existUser"] = false; // 존재하지 않는 유저
  $response["Validate"] = false; // 오늘 사용한 아이디일 경우, 존재하지 않는 유저인 경우 승인 x

}

$result2 = mysqli_query($con, "SELECT nowDate FROM DustTagID WHERE num = 1;");
//var_dump($result2);
$row = mysqli_fetch_array($result2);
//array_push($preDate, array("nowDate"=>$row[0]));

//var_dump($row[0]);
//echo $row[0];
if(strtotime($row[0]) < strtotime($nowTime)){
  $result3 = mysqli_query($con, 'TRUNCATE TABLE DustTagID'); // 테이블 초기화
}


//$preDate = "nowDate"=>$row[0];
//var_dump($preDate);

//mysql_query('DELETE FROM tablename');
$response["OnOff"] = true;


echo json_encode($response);
 ?>
