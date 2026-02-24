<!doctype html>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
</head>
<body style="background-color: rgb(66, 70, 73);">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <a href="/">
                    <img src="${resource(dir:'images',file:'jolly_logo.png')}" alt="Jolly Joe logo" />
                </a>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-5 col-sm-offset-1" style="color: white;">
                <h4 style="font-family: 'Segoe Script', cursive; font-size: 2em; letter-spacing: 1px;">Welcome ${customer.firstName}!</h4>
                <h4 style="font-family: 'Segoe Script', cursive; font-size: 1.7em; letter-spacing: 1px;">
                    You have ${customer.awardPoints} point${customer.awardPoints == 1 ? '' : 's'}.
                </h4>
            </div>
            <div class="col-sm-4 col-sm-offset-1" style="max-width: 350px;">
                <g:textField name="phone" class="form-control" style="max-width: 300px; margin: 0 auto;" value="${customer.phone}" readonly="true" />
                <!-- Keypad can be rendered here if needed -->
            </div>
        </div>
    </div>
    <g:javascript library="jquery"/>
    <script src="${resource(dir: 'javascript', file: 'bootstrap.min.js')}" type="text/javascript"></script>
</body>
</html>