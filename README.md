# Cold Mail Sender

A Spring Boot command-line application that sends personalized cold emails from an XML template with a PDF attachment.

## What it does
- Loads subject/body from `src/main/resources/coldmail.xml`.
- Replaces `${name}` in the body with the part before `@` in each recipient email.
- Sends HTML email + attachment (`Narayana_Resume.pdf`) through SMTP.
- Waits between sends to reduce spam-risk (`cold-mail.delay-millis`).

## Prerequisites (before you run)
1. **Java 17+** installed.
2. **Maven 3.9+** installed (or use `./mvnw`).
3. A working SMTP account.
   - For Gmail, use an **App Password** (not your normal account password).
4. Outbound internet access to your SMTP host.

## Where to write email/password and recipients
You have two options:

### Option A (recommended): environment variables
Set these before running:
- `MAIL_USERNAME` = your sender Gmail address
- `MAIL_PASSWORD` = your Gmail app password
- `RECIPIENTS` = comma-separated recipient emails in one line
  - You can pass plain CSV: `a@x.com,b@y.com,c@z.com`
  - You can also pass quoted CSV: `"a@x.com","b@y.com","c@z.com"`

`MAIL_HOST` is optional and defaults to `smtp.gmail.com`, so you do not need to set it unless you want another SMTP server.

### Option B: write directly in `src/main/resources/application.yaml`
You can replace placeholders with fixed values:
```yaml
spring:
  mail:
    username: your_email@gmail.com
    password: your_app_password

cold-mail:
  recipients-csv: "a@x.com","b@y.com","c@z.com"
```

## Configuration
The app reads settings from `src/main/resources/application.yaml` and environment variables.

### Required settings
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `RECIPIENTS` (comma-separated list in one line)

### Optional settings
- `MAIL_HOST` (default: `smtp.gmail.com`)
- `MAIL_PORT` (default: `587`)
- `COLD_MAIL_ENABLED` (default: `true`)

## Quick run (Linux/macOS)
```bash
export MAIL_USERNAME="your_email@gmail.com"
export MAIL_PASSWORD="your_app_password"
export RECIPIENTS='"example@gmail.com","example2@gmail.com"'

./mvnw spring-boot:run
```

## Quick run (Windows PowerShell)
```powershell
$env:MAIL_USERNAME="your_email@gmail.com"
$env:MAIL_PASSWORD="your_app_password"
$env:RECIPIENTS='"example@gmail.com","example2@gmail.com"'

.\mvnw.cmd spring-boot:run
```

## Attachment file name behavior
`cold-mail.attachment-path` controls the attachment resource file. The sent attachment name is automatically the same as the file name from that path.

Example:
- `cold-mail.attachment-path: Narayana_Resume.pdf`
- Email attachment name will be `Narayana_Resume.pdf`

## Build and run jar
```bash
./mvnw clean package
java -jar target/coldMailSender-0.0.1-SNAPSHOT.jar
```

## Final checks before run (green-signal checklist)
1. `MAIL_USERNAME` is set to your Gmail address.
2. `MAIL_PASSWORD` is set to your Gmail app password.
3. `RECIPIENTS` is set (plain CSV or quoted CSV).
4. `COLD_MAIL_ENABLED` is `true` (or unset).
5. Template/attachment files exist in `src/main/resources`.

If all 5 checks are true, you're good to go ✅

## Validate without sending emails
Set campaign disabled:
```bash
export COLD_MAIL_ENABLED=false
./mvnw spring-boot:run
```

## Customize campaign
In `application.yaml`:
- `cold-mail.template` → template file name (classpath)
- `cold-mail.attachment-path` → attachment resource path
- `cold-mail.delay-millis` → delay between sends
- `cold-mail.recipients-csv` → one-line comma-separated recipients

## Troubleshooting
- **"Missing mail credentials"** in logs:
  - Set `MAIL_USERNAME` and `MAIL_PASSWORD`.
- **"No recipients configured"** in logs:
  - Set `RECIPIENTS` with comma-separated emails.
- SMTP auth failures:
  - Verify host/port and credentials.
  - For Gmail, ensure 2FA + App Password is used.

## Test
```bash
./mvnw test
```
