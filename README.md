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

## Configuration
The app reads settings from `src/main/resources/application.yaml` and environment variables.

### Required environment variables
- `MAIL_USERNAME` → SMTP username / from-address
- `MAIL_PASSWORD` → SMTP password or app password
- `RECIPIENTS` → comma-separated list of recipients (for example `"a@example.com,b@example.com"`)

### Optional environment variables
- `MAIL_HOST` (default: `smtp.gmail.com`)
- `MAIL_PORT` (default: `587`)
- `COLD_MAIL_ENABLED` (default: `true`)

## Quick run (Linux/macOS)
```bash
export MAIL_USERNAME="your_email@gmail.com"
export MAIL_PASSWORD="your_app_password"
export RECIPIENTS="first.recipient@example.com,second.recipient@example.com"

./mvnw spring-boot:run
```

## Quick run (Windows PowerShell)
```powershell
$env:MAIL_USERNAME="your_email@gmail.com"
$env:MAIL_PASSWORD="your_app_password"
$env:RECIPIENTS="first.recipient@example.com,second.recipient@example.com"

.\mvnw.cmd spring-boot:run
```

## Build and run jar
```bash
./mvnw clean package
java -jar target/coldMailSender-0.0.1-SNAPSHOT.jar
```

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
- `cold-mail.attachment-name` → attachment display name
- `cold-mail.delay-millis` → delay between sends

## Troubleshooting
- **"Missing mail credentials"** in logs:
  - Set `MAIL_USERNAME` and `MAIL_PASSWORD`.
- **"No recipients configured"** in logs:
  - Set `RECIPIENTS` with a comma-separated list.
- SMTP auth failures:
  - Verify host/port and credentials.
  - For Gmail, ensure 2FA + App Password is used.
- `UnknownHostException: smtp.gmail.com`:
  - The runtime environment cannot resolve DNS / reach the internet. Check network, proxy, firewall, or run from a machine with outbound SMTP access.

## Test
```bash
./mvnw test
```
