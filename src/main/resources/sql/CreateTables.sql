DROP TABLE IF EXISTS GoalActivities;
DROP TABLE IF EXISTS Goal;
DROP TABLE IF EXISTS ActivityLog;
DROP TABLE IF EXISTS Activity;

CREATE TABLE Activity(
Id				INTEGER PRIMARY KEY AUTOINCREMENT,
Name			TEXT NOT NULL,
ExpectedDuration INTEGER NOT NULL,
Weight INTEGER NOT NULL,
Description		TEXT NOT NULL,
Type			INTEGER NOT NULL,
CreateAt		INTEGER NOT NULL
);

CREATE TABLE ActivityLog(
Id				INTEGER PRIMARY KEY AUTOINCREMENT,
Duration		INTEGER NOT NULL,
Description		TEXT,
Rating			INTEGER,
CreateAt		INTEGER NOT NULL,
ActivityId		INTEGER NOT NULL,

FOREIGN KEY (ActivityId) REFERENCES Activity (Id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE Goal(
Id				INTEGER PRIMARY KEY AUTOINCREMENT,
Name			TEXT NOT NULL,
CompletionWeight INTEGER NOT NULL,
Duration		INTEGER NOT NULL,
CreateAt		INTEGER NOT NULL,
ExpectedFinish	INTEGER,
Finished		INTEGER,
Type			INTEGER,
Percentage		REAL
);

CREATE TABLE GoalActivities(
Id				INTEGER PRIMARY KEY AUTOINCREMENT,
GoalId			INTEGER,
ActivityId		INTEGER,

FOREIGN KEY (ActivityId) REFERENCES Activity (Id) ON DELETE CASCADE ON UPDATE NO ACTION,
FOREIGN KEY (GoalId) REFERENCES Goal (Id) ON DELETE CASCADE ON UPDATE NO ACTION
);