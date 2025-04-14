<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Freemarker</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
Hello ${title}!${app}
<img src="/images/user-logo.png">

<script>
    $(document).ready(function() {
        $.ajax({
            url: "/api/v1/auth/request-code",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({mobile: '09128855402'}),
            success: function(dto) {
                console.log(dto);
            }
        });
    });
</script>
</body>
</html>