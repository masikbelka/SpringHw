USE [master]
GO
/****** Object:  Database [spring]    Script Date: 17.12.2015 12:26:21 PM ******/
CREATE DATABASE [spring]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'spring', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\spring.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'spring_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\spring_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [spring] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [spring].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [spring] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [spring] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [spring] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [spring] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [spring] SET ARITHABORT OFF 
GO
ALTER DATABASE [spring] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [spring] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [spring] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [spring] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [spring] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [spring] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [spring] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [spring] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [spring] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [spring] SET  DISABLE_BROKER 
GO
ALTER DATABASE [spring] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [spring] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [spring] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [spring] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [spring] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [spring] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [spring] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [spring] SET RECOVERY FULL 
GO
ALTER DATABASE [spring] SET  MULTI_USER 
GO
ALTER DATABASE [spring] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [spring] SET DB_CHAINING OFF 
GO
ALTER DATABASE [spring] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [spring] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [spring] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'spring', N'ON'
GO
USE [spring]
GO
/****** Object:  Table [dbo].[event]    Script Date: 17.12.2015 12:26:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[event](
	[event_id] [bigint] IDENTITY(1,1) NOT NULL,
	[event_title] [nvarchar](100) NOT NULL,
	[event_date] [datetime] NOT NULL,
	[event_ticket_price] [real] NOT NULL,
 CONSTRAINT [PK_event] PRIMARY KEY CLUSTERED 
(
	[event_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ticket]    Script Date: 17.12.2015 12:26:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticket](
	[ticket_id] [bigint] IDENTITY(1,1) NOT NULL,
	[event_id] [bigint] NOT NULL,
	[user_id] [bigint] NOT NULL,
	[ticket_category_id] [bigint] NOT NULL,
	[place] [int] NOT NULL,
 CONSTRAINT [PK_ticket] PRIMARY KEY CLUSTERED 
(
	[ticket_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ticket_category]    Script Date: 17.12.2015 12:26:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticket_category](
	[ticket_category_id] [bigint] IDENTITY(1,1) NOT NULL,
	[ticket_category_name] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_ticket_category] PRIMARY KEY CLUSTERED 
(
	[ticket_category_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[user]    Script Date: 17.12.2015 12:26:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user](
	[user_id] [bigint] IDENTITY(1,1) NOT NULL,
	[user_name] [nvarchar](max) NOT NULL,
	[user_email] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[user_account]    Script Date: 17.12.2015 12:26:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_account](
	[user_account_id] [bigint] IDENTITY(1,1) NOT NULL,
	[user_id] [bigint] NOT NULL,
	[user_account_funds] [real] NOT NULL,
 CONSTRAINT [PK_user_account] PRIMARY KEY CLUSTERED 
(
	[user_account_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[event] ON 

INSERT [dbo].[event] ([event_id], [event_title], [event_date], [event_ticket_price]) VALUES (1, N'First Event', CAST(N'2015-07-01 00:00:00.000' AS DateTime), 10)
INSERT [dbo].[event] ([event_id], [event_title], [event_date], [event_ticket_price]) VALUES (2, N'Second Event', CAST(N'2015-06-01 00:00:00.000' AS DateTime), 20)
INSERT [dbo].[event] ([event_id], [event_title], [event_date], [event_ticket_price]) VALUES (3, N'Third Event', CAST(N'2015-08-01 00:00:00.000' AS DateTime), 30)
INSERT [dbo].[event] ([event_id], [event_title], [event_date], [event_ticket_price]) VALUES (4, N'Fourth Event', CAST(N'2015-09-01 00:00:00.000' AS DateTime), 40)
SET IDENTITY_INSERT [dbo].[event] OFF
SET IDENTITY_INSERT [dbo].[ticket] ON 

INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (1, 1, 1, 1, 1)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (2, 1, 2, 1, 2)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (3, 1, 3, 1, 3)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (4, 1, 4, 1, 4)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (5, 2, 2, 1, 1)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (6, 2, 3, 1, 2)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (7, 2, 4, 1, 3)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (8, 3, 3, 1, 1)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (9, 3, 4, 1, 2)
INSERT [dbo].[ticket] ([ticket_id], [event_id], [user_id], [ticket_category_id], [place]) VALUES (10, 4, 4, 1, 1)
SET IDENTITY_INSERT [dbo].[ticket] OFF
SET IDENTITY_INSERT [dbo].[ticket_category] ON 

INSERT [dbo].[ticket_category] ([ticket_category_id], [ticket_category_name]) VALUES (1, N'STANDARD')
INSERT [dbo].[ticket_category] ([ticket_category_id], [ticket_category_name]) VALUES (2, N'PREMIUM')
INSERT [dbo].[ticket_category] ([ticket_category_id], [ticket_category_name]) VALUES (3, N'BAR')
SET IDENTITY_INSERT [dbo].[ticket_category] OFF
SET IDENTITY_INSERT [dbo].[user] ON 

INSERT [dbo].[user] ([user_id], [user_name], [user_email]) VALUES (1, N'First User', N'first@mail.com')
INSERT [dbo].[user] ([user_id], [user_name], [user_email]) VALUES (2, N'Second User', N'second@mail.com')
INSERT [dbo].[user] ([user_id], [user_name], [user_email]) VALUES (3, N'Third User', N'third@mail.com')
INSERT [dbo].[user] ([user_id], [user_name], [user_email]) VALUES (4, N'Fourth User', N'fourth@mail.com')
SET IDENTITY_INSERT [dbo].[user] OFF
SET IDENTITY_INSERT [dbo].[user_account] ON 

INSERT [dbo].[user_account] ([user_account_id], [user_id], [user_account_funds]) VALUES (1, 1, 100)
INSERT [dbo].[user_account] ([user_account_id], [user_id], [user_account_funds]) VALUES (2, 2, 200)
INSERT [dbo].[user_account] ([user_account_id], [user_id], [user_account_funds]) VALUES (3, 3, 300)
INSERT [dbo].[user_account] ([user_account_id], [user_id], [user_account_funds]) VALUES (4, 4, 400)
SET IDENTITY_INSERT [dbo].[user_account] OFF
ALTER TABLE [dbo].[user_account]  WITH CHECK ADD  CONSTRAINT [FK_user_account_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[user] ([user_id])
GO
ALTER TABLE [dbo].[user_account] CHECK CONSTRAINT [FK_user_account_user]
GO
USE [master]
GO
ALTER DATABASE [spring] SET  READ_WRITE 
GO
