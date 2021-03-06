USE [master]
GO
/****** Object:  Database [HanaShop]    Script Date: 3/1/2020 6:16:22 PM ******/
CREATE DATABASE [HanaShop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'HanaShop', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\HanaShop.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'HanaShop_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\HanaShop_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [HanaShop] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [HanaShop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [HanaShop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [HanaShop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [HanaShop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [HanaShop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [HanaShop] SET ARITHABORT OFF 
GO
ALTER DATABASE [HanaShop] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [HanaShop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [HanaShop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [HanaShop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [HanaShop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [HanaShop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [HanaShop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [HanaShop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [HanaShop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [HanaShop] SET  DISABLE_BROKER 
GO
ALTER DATABASE [HanaShop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [HanaShop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [HanaShop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [HanaShop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [HanaShop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [HanaShop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [HanaShop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [HanaShop] SET RECOVERY FULL 
GO
ALTER DATABASE [HanaShop] SET  MULTI_USER 
GO
ALTER DATABASE [HanaShop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [HanaShop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [HanaShop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [HanaShop] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [HanaShop] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'HanaShop', N'ON'
GO
USE [HanaShop]
GO
/****** Object:  Table [dbo].[Bills]    Script Date: 3/1/2020 6:16:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Bills](
	[idBill] [int] NOT NULL,
	[username] [varchar](50) NULL,
	[buyDate] [datetime] NULL,
	[numOfItems] [int] NULL,
	[total] [int] NULL,
 CONSTRAINT [PK_Bills] PRIMARY KEY CLUSTERED 
(
	[idBill] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 3/1/2020 6:16:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Categories](
	[categoryID] [int] NOT NULL,
	[nameCategory] [varchar](50) NULL,
 CONSTRAINT [PK_Categories] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ItemDelete]    Script Date: 3/1/2020 6:16:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ItemDelete](
	[username] [varchar](50) NOT NULL,
	[nameItem] [varchar](50) NOT NULL,
	[deleteDate] [datetime] NOT NULL,
 CONSTRAINT [PK_ItemDelete] PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[nameItem] ASC,
	[deleteDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Items]    Script Date: 3/1/2020 6:16:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Items](
	[nameItem] [varchar](50) NOT NULL,
	[image] [varchar](max) NULL,
	[description] [varchar](50) NULL,
	[price] [int] NULL,
	[createDate] [datetime] NULL,
	[amount] [int] NULL,
	[categoryID] [int] NULL,
	[statusID] [int] NULL,
	[numOfSearch] [int] NULL,
 CONSTRAINT [PK_Items] PRIMARY KEY CLUSTERED 
(
	[nameItem] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ItemUpdate]    Script Date: 3/1/2020 6:16:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ItemUpdate](
	[username] [varchar](50) NOT NULL,
	[nameItem] [varchar](50) NOT NULL,
	[updateDate] [datetime] NOT NULL,
 CONSTRAINT [PK_ItemUpdate] PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[nameItem] ASC,
	[updateDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Registration]    Script Date: 3/1/2020 6:16:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Registration](
	[username] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[role] [varchar](50) NULL,
 CONSTRAINT [PK_Registration] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ShoppingCart]    Script Date: 3/1/2020 6:16:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ShoppingCart](
	[username] [varchar](50) NOT NULL,
	[nameItem] [varchar](50) NOT NULL,
	[buyDate] [datetime] NOT NULL,
	[amount] [int] NULL,
	[price] [int] NULL,
	[total] [int] NULL,
	[idBill] [int] NULL,
 CONSTRAINT [PK_ShoppingCart_1] PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[nameItem] ASC,
	[buyDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Status]    Script Date: 3/1/2020 6:16:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Status](
	[statusID] [int] NOT NULL,
	[statusName] [varchar](50) NULL,
 CONSTRAINT [PK_Status] PRIMARY KEY CLUSTERED 
(
	[statusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Bills] ([idBill], [username], [buyDate], [numOfItems], [total]) VALUES (1, N'quynh30', CAST(N'2020-02-28 17:30:15.803' AS DateTime), 3, 1935000)
INSERT [dbo].[Bills] ([idBill], [username], [buyDate], [numOfItems], [total]) VALUES (2, N'quynh30', CAST(N'2020-02-28 18:42:52.947' AS DateTime), 1, 940000)
INSERT [dbo].[Bills] ([idBill], [username], [buyDate], [numOfItems], [total]) VALUES (3, N'quynh30', CAST(N'2020-02-29 00:22:36.180' AS DateTime), 2, 62000)
INSERT [dbo].[Bills] ([idBill], [username], [buyDate], [numOfItems], [total]) VALUES (4, N'quynh30', CAST(N'2020-02-29 22:21:14.617' AS DateTime), 2, 2650000)
INSERT [dbo].[Bills] ([idBill], [username], [buyDate], [numOfItems], [total]) VALUES (5, N'hoang25', CAST(N'2020-02-29 22:40:15.440' AS DateTime), 4, 771000)
INSERT [dbo].[Bills] ([idBill], [username], [buyDate], [numOfItems], [total]) VALUES (6, N'hoang25', CAST(N'2020-03-01 18:04:22.447' AS DateTime), 5, 3410000)
INSERT [dbo].[Categories] ([categoryID], [nameCategory]) VALUES (1, N'Drink')
INSERT [dbo].[Categories] ([categoryID], [nameCategory]) VALUES (2, N'Fast food')
INSERT [dbo].[Categories] ([categoryID], [nameCategory]) VALUES (3, N'Food')
INSERT [dbo].[ItemDelete] ([username], [nameItem], [deleteDate]) VALUES (N'adung5341', N'Ga', CAST(N'2020-03-01 17:30:02.237' AS DateTime))
INSERT [dbo].[ItemDelete] ([username], [nameItem], [deleteDate]) VALUES (N'adung5341', N'Heneiken', CAST(N'2020-03-01 17:26:55.533' AS DateTime))
INSERT [dbo].[ItemDelete] ([username], [nameItem], [deleteDate]) VALUES (N'adung5341', N'King crab', CAST(N'2020-03-01 17:31:11.113' AS DateTime))
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Banh trang tron', N'banhtrang.jpg', N'banh trang tron', 15000, CAST(N'2020-02-09 22:26:58.987' AS DateTime), 93, 2, 1, 7)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Bun bo', N'bunbo2.jpg', N'bun bo hue', 35000, CAST(N'2020-02-09 22:24:44.253' AS DateTime), 200, 3, 1, 5)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Cafe', N'cafe.jpg', N'cafe sua', 25000, CAST(N'2020-02-09 12:50:44.530' AS DateTime), 95, 1, 1, 7)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'CocaCola', N'coca.jpg', N'Nuoc ngot co ga', 8000, CAST(N'2020-02-09 21:46:10.223' AS DateTime), 194, 1, 1, 8)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Com chien', N'com.jpg', N'Com chien duong chau', 35000, CAST(N'2020-02-09 22:26:20.047' AS DateTime), 100, 2, 1, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Ga', N'ga.jpg', N'ga ran', 235000, CAST(N'2020-02-12 11:02:24.063' AS DateTime), 95, 2, 1, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Heneiken', N'bia.jpg', N'Bia chai', 15000, CAST(N'2020-02-09 10:25:35.333' AS DateTime), 0, 1, 2, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Khoai tay chien', N'khoai.png', N'khoai tay chien', 20000, CAST(N'2020-02-09 22:49:36.237' AS DateTime), 100, 2, 2, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'King crab', N'cua.jpg', N'cua hoang de', 1300000, CAST(N'2020-02-12 11:08:00.307' AS DateTime), 198, 3, 2, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Monster', N'monster.jpg', N'Nuoc tang luc', 55000, CAST(N'2020-02-09 22:46:40.593' AS DateTime), 136, 1, 1, 6)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Pizza 2', N'pizza2.jpg', N'banh pizza', 475000, CAST(N'2020-02-25 22:19:02.193' AS DateTime), 249, 2, 1, 10)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Poca', N'poca.jpg', N'snack khoai tay', 5000, CAST(N'2020-02-09 22:49:06.063' AS DateTime), 100, 2, 2, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Red Bull', N'redbull.jpg', N'Nuoc tang luc', 15000, CAST(N'2020-02-25 22:13:03.367' AS DateTime), 150, 1, 2, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Rio', N'rio.jpg', N'nuoc uong rio', 760000, CAST(N'2020-03-01 17:59:08.100' AS DateTime), 148, 1, 1, 1)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'SashimiJP', N'sashimi.jpg', N'sashimi jp', 850000, CAST(N'2020-02-12 10:47:09.303' AS DateTime), 95, 3, 1, 9)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Socola', N'socola.jpg', N'socola ', 600000, CAST(N'2020-02-25 22:20:20.317' AS DateTime), 142, 2, 1, 8)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Tra sua', N'trasua.jpg', N'tra sua socola', 40000, CAST(N'2020-02-10 10:33:40.337' AS DateTime), 100, 1, 2, NULL)
INSERT [dbo].[Items] ([nameItem], [image], [description], [price], [createDate], [amount], [categoryID], [statusID], [numOfSearch]) VALUES (N'Xuc xich', N'xucxich.jpg', N'Xuc xich Duc chien', 20000, CAST(N'2020-02-09 22:25:45.367' AS DateTime), 100, 2, 2, NULL)
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Bun bo', CAST(N'2020-02-17 23:21:04.687' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Bun bo', CAST(N'2020-03-01 17:57:28.997' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Cafe', CAST(N'2020-02-12 11:15:25.137' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Cafe', CAST(N'2020-02-21 22:41:56.527' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'CocaCola', CAST(N'2020-02-12 11:15:53.373' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Com chien', CAST(N'2020-02-12 22:33:58.907' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Ga', CAST(N'2020-02-21 22:24:01.437' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Heneiken', CAST(N'2020-02-21 22:41:50.190' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Khoai tay chien', CAST(N'2020-02-17 23:56:34.290' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'King crab', CAST(N'2020-02-17 23:20:48.010' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Pizza 2', CAST(N'2020-03-01 17:15:53.103' AS DateTime))
INSERT [dbo].[ItemUpdate] ([username], [nameItem], [updateDate]) VALUES (N'adung5341', N'Xuc xich', CAST(N'2020-02-17 23:21:15.207' AS DateTime))
INSERT [dbo].[Registration] ([username], [password], [name], [role]) VALUES (N'adung5341', N'2341999', N'Giang Luu Anh Dung', N'admin')
INSERT [dbo].[Registration] ([username], [password], [name], [role]) VALUES (N'hoang25', N'2581997', N'Thanh Hoang', N'member')
INSERT [dbo].[Registration] ([username], [password], [name], [role]) VALUES (N'quynh30', N'30092003', N'Diem Quynh', N'member')
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Banh trang tron', CAST(N'2020-03-01 18:04:22.447' AS DateTime), 4, 15000, 60000, 6)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Cafe', CAST(N'2020-02-29 22:40:15.440' AS DateTime), 4, 25000, 100000, 5)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'CocaCola', CAST(N'2020-02-29 22:40:15.440' AS DateTime), 2, 8000, 16000, 5)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Ga', CAST(N'2020-03-01 18:04:22.447' AS DateTime), 5, 235000, 1175000, 6)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Monster', CAST(N'2020-02-29 22:40:15.440' AS DateTime), 1, 55000, 55000, 5)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Monster', CAST(N'2020-03-01 18:04:22.447' AS DateTime), 1, 55000, 55000, 6)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Rio', CAST(N'2020-03-01 18:04:22.447' AS DateTime), 2, 760000, 1520000, 6)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Socola', CAST(N'2020-02-29 22:40:15.440' AS DateTime), 1, 600000, 600000, 5)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'hoang25', N'Socola', CAST(N'2020-03-01 18:04:22.447' AS DateTime), 1, 600000, 600000, 6)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'Banh trang tron', CAST(N'2020-02-29 00:22:36.180' AS DateTime), 2, 15000, 30000, 3)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'Cafe', CAST(N'2020-02-28 17:30:15.803' AS DateTime), 1, 25000, 25000, 1)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'CocaCola', CAST(N'2020-02-29 00:22:36.180' AS DateTime), 4, 8000, 32000, 3)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'Monster', CAST(N'2020-02-28 17:30:15.803' AS DateTime), 2, 55000, 110000, 1)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'Pizza 2', CAST(N'2020-02-28 18:42:52.947' AS DateTime), 2, 470000, 940000, 2)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'SashimiJP', CAST(N'2020-02-29 22:21:14.617' AS DateTime), 1, 850000, 850000, 4)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'Socola', CAST(N'2020-02-28 17:30:15.803' AS DateTime), 3, 600000, 1800000, 1)
INSERT [dbo].[ShoppingCart] ([username], [nameItem], [buyDate], [amount], [price], [total], [idBill]) VALUES (N'quynh30', N'Socola', CAST(N'2020-02-29 22:21:14.617' AS DateTime), 3, 600000, 1800000, 4)
INSERT [dbo].[Status] ([statusID], [statusName]) VALUES (1, N'Active')
INSERT [dbo].[Status] ([statusID], [statusName]) VALUES (2, N'Inactive')
ALTER TABLE [dbo].[Bills]  WITH CHECK ADD  CONSTRAINT [FK_Bills_Registration] FOREIGN KEY([username])
REFERENCES [dbo].[Registration] ([username])
GO
ALTER TABLE [dbo].[Bills] CHECK CONSTRAINT [FK_Bills_Registration]
GO
ALTER TABLE [dbo].[ItemDelete]  WITH CHECK ADD  CONSTRAINT [FK_ItemDelete_Items] FOREIGN KEY([nameItem])
REFERENCES [dbo].[Items] ([nameItem])
GO
ALTER TABLE [dbo].[ItemDelete] CHECK CONSTRAINT [FK_ItemDelete_Items]
GO
ALTER TABLE [dbo].[ItemDelete]  WITH CHECK ADD  CONSTRAINT [FK_ItemDelete_Registration] FOREIGN KEY([username])
REFERENCES [dbo].[Registration] ([username])
GO
ALTER TABLE [dbo].[ItemDelete] CHECK CONSTRAINT [FK_ItemDelete_Registration]
GO
ALTER TABLE [dbo].[Items]  WITH CHECK ADD  CONSTRAINT [FK_Items_Categories] FOREIGN KEY([categoryID])
REFERENCES [dbo].[Categories] ([categoryID])
GO
ALTER TABLE [dbo].[Items] CHECK CONSTRAINT [FK_Items_Categories]
GO
ALTER TABLE [dbo].[Items]  WITH CHECK ADD  CONSTRAINT [FK_Items_Status] FOREIGN KEY([statusID])
REFERENCES [dbo].[Status] ([statusID])
GO
ALTER TABLE [dbo].[Items] CHECK CONSTRAINT [FK_Items_Status]
GO
ALTER TABLE [dbo].[ItemUpdate]  WITH CHECK ADD  CONSTRAINT [FK_ItemUpdate_Items] FOREIGN KEY([nameItem])
REFERENCES [dbo].[Items] ([nameItem])
GO
ALTER TABLE [dbo].[ItemUpdate] CHECK CONSTRAINT [FK_ItemUpdate_Items]
GO
ALTER TABLE [dbo].[ItemUpdate]  WITH CHECK ADD  CONSTRAINT [FK_ItemUpdate_Registration] FOREIGN KEY([username])
REFERENCES [dbo].[Registration] ([username])
GO
ALTER TABLE [dbo].[ItemUpdate] CHECK CONSTRAINT [FK_ItemUpdate_Registration]
GO
ALTER TABLE [dbo].[ShoppingCart]  WITH CHECK ADD  CONSTRAINT [FK_ShoppingCart_Bills] FOREIGN KEY([idBill])
REFERENCES [dbo].[Bills] ([idBill])
GO
ALTER TABLE [dbo].[ShoppingCart] CHECK CONSTRAINT [FK_ShoppingCart_Bills]
GO
ALTER TABLE [dbo].[ShoppingCart]  WITH CHECK ADD  CONSTRAINT [FK_ShoppingCart_Items] FOREIGN KEY([nameItem])
REFERENCES [dbo].[Items] ([nameItem])
GO
ALTER TABLE [dbo].[ShoppingCart] CHECK CONSTRAINT [FK_ShoppingCart_Items]
GO
ALTER TABLE [dbo].[ShoppingCart]  WITH CHECK ADD  CONSTRAINT [FK_ShoppingCart_Registration] FOREIGN KEY([username])
REFERENCES [dbo].[Registration] ([username])
GO
ALTER TABLE [dbo].[ShoppingCart] CHECK CONSTRAINT [FK_ShoppingCart_Registration]
GO
USE [master]
GO
ALTER DATABASE [HanaShop] SET  READ_WRITE 
GO
