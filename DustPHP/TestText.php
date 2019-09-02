<?
$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

header("Content-Type:text/html;charset=utf-8");


$Test = $_POST["Test"];
$num = 0;

$statement = mysqli_prepare($con, "INSERT INTO TestText VALUES (?, ?)"); //
mysqli_stmt_bind_param($statement, "is", $num, $Test);
mysqli_stmt_execute($statement);


$response = array();

$response["success"] = false;
$response["kwon"] = 'CiBal zzz';
$response["kwon11"] = $Test;

echo json_encode($response);

?>
