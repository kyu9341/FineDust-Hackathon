<?php
$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

header("Content-Type:text/html;charset=utf-8");


$ID = $_POST["ID"];
$num = 0;
//$ID = 'afas';
$nowTime = date("Y-m-d", time());
//$nowTime = "2019-06-26";
$result1 = mysqli_query($con, "SELECT * FROM DustTagID WHERE ID = '$ID';");

$response = array();

if($result1->num_rows >= 1) {

  $response["Validate"] = false;

}else {

    $result = mysqli_prepare($con, "INSERT INTO DustTagID VALUES (?, ?, ?)");
    mysqli_stmt_bind_param($result, "iss", $num, $ID, $nowTime);
    mysqli_stmt_execute($result);

  $response["Validate"] = true;
//  $response["ID"] = $ID;

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
$response["OnOff"] = false;


echo json_encode($response);
 ?>
