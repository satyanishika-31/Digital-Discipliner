# Habit Tracker - Deployment Guide

## Your JAR file is ready!
Location: `target/habit-0.0.1-SNAPSHOT.jar`

---

## Option 1: Run Locally or on Any Server

### Requirements:
- Java 21 or higher
- MySQL 8.0+

### Steps:
1. Copy `habit-0.0.1-SNAPSHOT.jar` to your server
2. Ensure MySQL is running with database `habit_db`
3. Run:
   ```bash
   java -jar habit-0.0.1-SNAPSHOT.jar
   ```
4. Access at: http://localhost:8081

---

## Option 2: Deploy to Heroku (Free/Paid)

### Prerequisites:
- Install [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli)
- Git installed

### Steps:
```bash
# 1. Initialize git repository (if not done)
cd d:\ip-ll\ip_project_2\habit
git init
git add .
git commit -m "Initial commit"

# 2. Login to Heroku
heroku login

# 3. Create Heroku app
heroku create your-app-name

# 4. Add MySQL ClearDB addon
heroku addons:create cleardb:ignite

# 5. Get database URL
heroku config:get CLEARDB_DATABASE_URL

# 6. Set database configuration
heroku config:set SPRING_DATASOURCE_URL=jdbc:mysql://[host]:[port]/[database]
heroku config:set SPRING_DATASOURCE_USERNAME=[username]
heroku config:set SPRING_DATASOURCE_PASSWORD=[password]

# 7. Deploy
git push heroku main  # or master
```

Your app will be live at: https://your-app-name.herokuapp.com

---

## Option 3: Deploy with Docker

### Build Docker image:
```bash
cd d:\ip-ll\ip_project_2\habit
docker build -t habit-tracker .
```

### Run with Docker:
```bash
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/habit_db \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=nishika0205 \
  habit-tracker
```

---

## Option 4: Deploy to Railway (Easiest)

1. Go to [Railway.app](https://railway.app)
2. Sign up with GitHub
3. Click "New Project" → "Deploy from GitHub repo"
4. Select your repository
5. Railway auto-detects Spring Boot
6. Add MySQL database:
   - Click "+ New" → "Database" → "MySQL"
7. Railway provides URL automatically

---

## Option 5: Deploy to Render

1. Go to [Render.com](https://render.com)
2. Create new "Web Service"
3. Connect GitHub repository
4. Build Command: `./mvnw clean package -DskipTests`
5. Start Command: `java -jar target/habit-0.0.1-SNAPSHOT.jar`
6. Add MySQL database from Render dashboard

---

## ⚠️ IMPORTANT: Before Production Deployment

### Update application.properties:

Replace hardcoded credentials with environment variables:

```properties
# Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:mysql://localhost:3306/habit_db}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD}

# Production Settings
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.org.hibernate.SQL=ERROR

# Security (generate strong password)
spring.security.user.password=${ADMIN_PASSWORD:changeme}
```

### Set Environment Variables:
```bash
# Heroku
heroku config:set DB_USERNAME=your_username
heroku config:set DB_PASSWORD=your_password
heroku config:set ADMIN_PASSWORD=securepassword123

# Docker
docker run -e DB_USERNAME=root -e DB_PASSWORD=secure123 ...
```

---

## Testing Your Deployment

After deployment, test these endpoints:
- `GET /` - Main page
- `GET /login` - Login page
- `GET /habit` - Habit tracker (requires login)
- `POST /api/register` - Registration API
- `POST /api/login` - Login API

---

## Troubleshooting

### Application won't start:
- Check Java version: `java -version` (should be 21+)
- Verify MySQL is running: `mysql -u root -p`
- Check port 8081 is available

### Database connection error:
- Verify MySQL credentials in application.properties
- Check database `habit_db` exists
- Test connection: `mysql -u root -p habit_db`

### Need help?
Check logs:
```bash
# When running locally
java -jar habit-0.0.1-SNAPSHOT.jar --debug

# Heroku logs
heroku logs --tail
```

---

## Recommended: Railway or Render
For beginners, **Railway** and **Render** are the easiest - they handle everything automatically!
