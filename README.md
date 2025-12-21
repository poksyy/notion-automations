# Notion Automations

Automation scripts for managing my Notion workspace, starting with daily habit tracking.

## 🚀 Features

- **Daily Habit Automation**: Automatically creates a new day entry and all habit entries, fully linked together
- Clean architecture with separation of concerns
- GitHub Actions for scheduled execution
- Environment-based configuration (no hardcoded secrets)

## 📁 Project Structure

```
notion-automations/
├── src/main/java/com/poksy/
│   ├── App.java                      # Entry point
│   ├── config/
│   │   └── NotionConfig.java         # Configuration management
│   ├── client/
│   │   └── NotionClient.java         # HTTP client for Notion API
│   ├── model/
│   │   ├── Day.java                  # Day model
│   │   └── Habit.java                # Habit model
│   ├── service/
│   │   ├── DayService.java           # Day operations
│   │   └── HabitService.java         # Habit operations
│   └── automation/
│       └── DailyHabitAutomation.java # Main automation
├── .github/workflows/
│   └── daily-habits.yml              # GitHub Actions workflow
├── .env.example                      # Example environment config
├── pom.xml                           # Maven configuration
└── README.md
```

## ⚙️ Setup

### 1. Get Notion Credentials

1. Go to [notion.so/profile/integrations](https://notion.so/profile/integrations)
2. Create a new integration
3. Copy the **Internal Integration Secret** (starts with `secret_`)
4. Connect the integration to your Habits page in Notion

### 2. Get Database IDs

Open each database in Notion and copy the ID from the URL:

```
https://notion.so/workspace/1234567890abcdef1234567890abcdef?v=...
                            └──────────── Database ID ──────────────┘
```

You need IDs for:
- **Days** database
- **Daily Log** database

### 3. Configure Environment

Copy `.env.example` to `.env` and fill in your values:

```bash
cp .env.example .env
```

### 4. Build & Run Locally

```bash
# Build
mvn clean package

# Run
java -jar target/notion-automations-1.0.0.jar habits
```

## 🤖 Automated Execution (GitHub Actions)

### Setup

1. Push this repository to GitHub
2. Go to **Settings** → **Secrets and variables** → **Actions**
3. Add these secrets:
   - `NOTION_TOKEN`
   - `DAYS_DATABASE_ID`
   - `DAILY_LOG_DATABASE_ID`

### Schedule

The automation runs automatically every day at **6:00 AM (Spanish time)**.

You can also trigger it manually from the **Actions** tab.

## 🔧 Customizing Habits

Edit `DailyHabitAutomation.java` to modify your habits:

```java
private static final List<Habit> HABITS = List.of(
    new Habit("Meditate", HabitGroup.MORNING),
    new Habit("Stretching", HabitGroup.MORNING),
    // Add or remove habits here
);
```

## 📝 Adding New Automations

1. Create a new class in `automation/` package
2. Use existing services or create new ones in `service/`
3. Add a new command in `App.java`
4. Create a new GitHub workflow if needed

## 🛠️ Tech Stack

- Java 17
- Maven
- [Notion API](https://developers.notion.com/reference/intro)
- GitHub Actions
- Gson (JSON parsing)
- dotenv-java (environment configuration)

## 📄 License

MIT
