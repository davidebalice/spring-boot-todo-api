<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>error</title>
</head>
<body>
   <h1>Error Page</h1>
    <#if errorMessage?has_content>
        <p>${errorMessage}</p>
    </#if>
</body>
</html>
