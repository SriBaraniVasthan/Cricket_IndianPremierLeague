import json
import random
import os

# IPL 2025 teams with home locations
teams = [
    {"name": "Chennai Super Kings", "location": "Chennai"},
    {"name": "Mumbai Indians", "location": "Mumbai"},
    {"name": "Kolkata Knight Riders", "location": "Kolkata"},
    {"name": "Royal Challengers Bangalore", "location": "Bengaluru"},
    {"name": "Delhi Capitals", "location": "Delhi"},
    {"name": "Punjab Kings", "location": "Mohali"},
    {"name": "Rajasthan Royals", "location": "Jaipur"},
    {"name": "Sunrisers Hyderabad", "location": "Hyderabad"},
    {"name": "Lucknow Super Giants", "location": "Lucknow"},
    {"name": "Gujarat Titans", "location": "Ahmedabad"}
]

output_dir = "./generated-data/"
os.makedirs(output_dir, exist_ok=True)

match_id = 1

for home_team in teams:
    home_location = home_team["location"]
    home_team_name = home_team["name"]

    for opponent in teams:
        if opponent["name"] != home_team_name:
            opponent_name = opponent["name"]

            if home_team_name == "Chennai Super Kings":
                runs_home = random.randint(180, 250)  
                runs_away = random.randint(50, 170)
            elif opponent_name == "Chennai Super Kings":
                runs_home = random.randint(50, 170)
                runs_away = random.randint(180, 250)  
            else:
                runs_home = random.randint(50, 250)
                runs_away = random.randint(50, 250)

            total_runs = runs_home + runs_away
            win_percentage_home = round((runs_home / total_runs) * 100, 2) if total_runs > 0 else 0
            win_percentage_away = round((runs_away / total_runs) * 100, 2) if total_runs > 0 else 0

            match_result = {
                "id": match_id,
                "name": home_location,
                "seqNo": match_id,
                "teamResults": [
                    {
                        "team": home_team_name,
                        "runs": runs_home,
                        "winPercentage": win_percentage_home
                    },
                    {
                        "team": opponent_name,
                        "runs": runs_away,
                        "winPercentage": win_percentage_away
                    }
                ]
            }

            filename = f"match{match_id:03}.json"
            with open(os.path.join(output_dir, filename), "w") as f:
                json.dump(match_result, f, indent=2)

            print(f"Generated {filename} for {home_team_name} vs {opponent_name} at {home_location}")
            match_id += 1
