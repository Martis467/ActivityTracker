INSERT INTO Activity (Name, ExpectedDurations, Description, Type, CreatedAt, HexColor, Disabled)
VALUES ('Activity1', 15, 'Doing activity1', 2, 1564162450, 4360181, 0);

INSERT INTO Activity (Name, ExpectedDurations, Description, Type, CreatedAt, HexColor, Disabled)
VALUES ('Activity2', 15, 'Doing activity2', 1, 1564162450, 4360181,0);

INSERT INTO Activity (Name, ExpectedDurations, Description, Type, CreatedAt, HexColor, Disabled)
VALUES ('Activity3', 15, 'Doing activity3', 0, 1564162450, 4360181, 0);

INSERT INTO Goal (Name, CompletionWeight, Description, CreatedAt, ExpectedFinish, Finished, Type, Percentage, HexColor, Completed)
VALUES ('Goal1', 1500, 'Doing Goal1', 1564162450, 1564163450, NULL, 2, 0, 4360181, 0);

INSERT INTO Goal (Name, CompletionWeight, Description, CreatedAt, ExpectedFinish, Finished, Type, Percentage, HexColor, Completed)
VALUES ('Goal2', 1500, 'Doing Goal2', 1564162450, 1564163450, NULL, 3, 0, 4360181, 0);

INSERT INTO Goal (Name, CompletionWeight, Description, CreatedAt, ExpectedFinish, Finished, Type, Percentage, HexColor, Completed)
VALUES ('Goal3', 1500, 'Doing Goal3', 1564162450, 1564163450, NULL, 0, 0, 4360181, 0);

INSERT INTO ActivityLog (Duration, Rating, CreatedAt, CompletedAt, ActivityId)
VALUES (15, 3, 1564162450, 1564162450, 3);

INSERT INTO ActivityLog (Duration, Rating, CreatedAt, CompletedAt, ActivityId)
VALUES (30, 1, 1564162450, 0, 3);