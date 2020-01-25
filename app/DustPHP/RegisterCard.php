
<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $CardNum = $_GET["CardNum"];
  $ID = $_GET["ID"];

//  $CardNum = '213124';
//  $ID = "aaa";

  $result = mysqli_query($con, "UPDATE Dust_user SET CardNum = '$CardNum' WHERE ID = '$ID';");

//  var_dump($result);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
