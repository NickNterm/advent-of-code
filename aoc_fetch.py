import requests
import os
import sys

def fetch(day, year=2024):
    url = f"https://adventofcode.com/{year}/day/{day}/input"
    session = os.environ.get("AOC_SESSION")
    if not session:
        raise ValueError("No session found")
    cookies = {"session": session}
    r = requests.get(url, cookies=cookies)
    r.raise_for_status()
    with open(f"src/Day{day}.txt", "w") as f:
        f.write(r.text)

if __name__ == "__main__":
    fetch(sys.argv[1])
