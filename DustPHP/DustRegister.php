
<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $ID = $_POST["ID"];
  $password = $_POST["password"];
  $name = $_POST["name"];
  $phone = $_POST["phone"];
  $CardNum = 'noCard';

  $statement = mysqli_prepare($con, "INSERT INTO Dust_user VALUES (?, ?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "sssss", $ID, $password, $name, $phone, $CardNum);
  mysqli_stmt_execute($statement);


  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
