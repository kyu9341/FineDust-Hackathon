<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  $ID = $_POST["ID"];
  $password = $_POST["password"];

  $statement = mysqli_prepare($con, "SELECT * FROM Dust_user WHERE ID = ? AND password =?"); // 특정 ID와 password에 해당하는 회원이 존재하는지 확인
  mysqli_stmt_bind_param($statement, "ss", $ID, $password);
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $ID);


  $response = array();  //
  $response["success"] = false;

  while(mysqli_stmt_fetch($statement)){
    $response["success"] = true;
    $response["ID"] = $ID;

  }

  echo json_encode($response);

  mysqli_close($con);


 ?>
