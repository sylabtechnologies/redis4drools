<div class="fieldcontain">
    <label for="firstName">First Name</label>
    <g:textField name="firstName" value="${firstName}" />
</div>

<div class="fieldcontain">
    <label for="comments">Comments</label>
    <g:textArea name="comments" value="${comments}" />
</div>

<div class="fieldcontain">
    <label for="notifyByEmail">Notify via Email</label>
    <g:checkBox name="notifyByEmail" value="${notifyByEmail ?: false}" />
</div>

<div class="fieldcontain">
    <label for="referralSource">How did you hear about us?</label>
    <g:select name="referralSource"
              from="['Internet','Friend','Mailer', 'Other']"
              value="${referralSource}"
              />   <!-- noSelection="['':'- Choose -']" -->
</div>