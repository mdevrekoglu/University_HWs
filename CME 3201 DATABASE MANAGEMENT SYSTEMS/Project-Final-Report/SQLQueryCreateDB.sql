CREATE DATABASE CRITIFLIX
GO

USE CRITIFLIX;
GO

-- Create ImdbIDTable table
CREATE TABLE ImdbIDTable (
    ImdbID NVARCHAR(25) PRIMARY KEY,
    Type NVARCHAR(10)
);
GO

-- Create UserT table
CREATE TABLE UserT (
    Name NVARCHAR(15) NOT NULL,
    Surname NVARCHAR(15) NOT NULL,
    Mail NVARCHAR(50) NOT NULL UNIQUE,
    Username NVARCHAR(15) NOT NULL UNIQUE,
    Password NVARCHAR(64) NOT NULL,
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    PhotoData VARBINARY(MAX)
);
GO

-- Create movies table
CREATE TABLE movies (
    Title NVARCHAR(MAX) NOT NULL,
    Year INT NOT NULL,
    Released DATE,
    Runtime INT,
    Genre NVARCHAR(MAX) NOT NULL,
    Director NVARCHAR(MAX),
    Writer NVARCHAR(MAX),
    Actors NVARCHAR(MAX),
    Plot NVARCHAR(MAX),
    Language NVARCHAR(MAX) NOT NULL,
    Country NVARCHAR(MAX),
    Awards NVARCHAR(MAX),
    Poster NVARCHAR(MAX),
    MetaScore FLOAT(20),
    ImdbRating FLOAT(20),
    ImdbID NVARCHAR(25) PRIMARY KEY REFERENCES ImdbIDTable(ImdbID)
);
GO

-- Create series table
CREATE TABLE series (
    Title NVARCHAR(MAX) NOT NULL,
    StartYear INT NOT NULL,
    EndYear INT,
    Released DATE,
    Runtime INT,
    Genre NVARCHAR(MAX) NOT NULL,
    Writer NVARCHAR(MAX),
    Actors NVARCHAR(MAX),
    Plot NVARCHAR(MAX),
    Language NVARCHAR(MAX) NOT NULL,
    Country NVARCHAR(MAX),
    Awards NVARCHAR(MAX),
    Poster NVARCHAR(MAX),
    MetaScore FLOAT(20),
    ImdbRating FLOAT(20),
    ImdbID NVARCHAR(25) PRIMARY KEY REFERENCES ImdbIDTable(ImdbID),
    TotalSeasons INT NOT NULL
);
GO

-- Create MovSerLists table
CREATE TABLE MovSerLists (
    ListID INT IDENTITY(1,1) PRIMARY KEY,
    ListName NVARCHAR(50),
    ListOwnerID INT REFERENCES UserT(UserID),
    UNIQUE(ListName, ListOwnerID)
);
GO

-- Create ListsRelationT table
CREATE TABLE ListsRelationT (
    ListID INT REFERENCES MovSerLists(ListID),
    ImdbID NVARCHAR(25) REFERENCES ImdbIDTable(ImdbID),
    CONSTRAINT PK_ListsRelationT PRIMARY KEY (ListID, ImdbID)
);
GO

-- Create PostT table
CREATE TABLE PostT (
    PostID INT IDENTITY(1,1) PRIMARY KEY,
    ImdbID NVARCHAR(25) REFERENCES ImdbIDTable(ImdbID),
    UserID INT REFERENCES UserT(UserID),
    PostDate DATE,
    Content NVARCHAR(MAX)
);
GO

-- Create PostLikeT table
CREATE TABLE PostLikeT (
    PostID INT REFERENCES PostT(PostID),
    UserID INT REFERENCES UserT(UserID),
    CONSTRAINT PK_PostLikeT PRIMARY KEY (PostID, UserID)
);
GO

-- Create CommentT table
CREATE TABLE CommentT (
    CommentID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT REFERENCES UserT(UserID),
    PostID INT REFERENCES PostT(PostID),
    CommentDate DATE,
    Content NVARCHAR(MAX)
);
GO

-- Create FollowingT table
CREATE TABLE FollowingT (
    FollowerID INT REFERENCES UserT(UserID),
    FolloweeID INT REFERENCES UserT(UserID),
    CONSTRAINT PK_FollowingT PRIMARY KEY (FollowerID, FolloweeID)
);
