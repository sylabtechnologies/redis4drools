// simple client-side behavior for customer checkin
// validates phone number and submits the form via AJAX if valid

(function($){
    $(document).ready(function(){
        var form = $('form');
        form.on('submit', function(e){
            e.preventDefault();
            var phone = $('input[name="phone"]').val();
            if(!phone || !/^[0-9]{7,15}$/.test(phone)){
                alert('Please enter a valid phone number');
                return false;
            }
            // optionally show a spinner / disable inputs
            $.ajax({
                url: form.attr('action'),
                method: form.attr('method') || 'POST',
                data: form.serialize(),
                success: function(data){
                    // assuming controller returns redirect or json
                    if(data && data.redirect){
                        window.location = data.redirect;
                    } else {
                        // fallback: reload
                        window.location.reload();
                    }
                },
                error: function(){
                    alert('Unable to contact server. Please try again.');
                }
            });
        });
    });
})(jQuery);