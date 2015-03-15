-------------------------  COSTCENTER --------------------------------- 
CREATE TABLE CostCenter(
	[id] [int] NOT NULL,
	[name] [varchar](70) NULL,
	[idEmployee] [int] NULL
 CONSTRAINT [pk_costcenter_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

------------------------  EMPLOYEE --------------------------------- 
CREATE TABLE Employee(
	[id] [int] IDENTITY(1,1) NOT NULL,
	[registry] [int] NULL,
	[name] [varchar](50) NULL,
	[email] [varchar](75) NULL,
	[position] [varchar](40) NULL,
	[operationArea] [varchar](25) NULL,
	[idCostCenter] [int] NULL,
	[phone] [varchar](15) NULL,
	[operationProduct] [varchar](40) NULL,
	[password] [varchar](100) NULL,
	[login] [varchar](255) NOT NULL,
	[contract] [varchar](10) NULL,
	[changePassword] [bit] NOT NULL,
	[active] [bit] NOT NULL,
	[cpf] [varchar](14) NULL
CONSTRAINT [pk_employee_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [uk_employee_login] UNIQUE NONCLUSTERED 
(
	[login] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-------------------------  ROLEEMPLOYEEPOOL --------------------------------- 
CREATE TABLE RoleEmployeePool(
	[idRole] [int] NOT NULL,
	[idEmployee] [int] NOT NULL,
	[idPool] [int] NOT NULL,
)

ALTER TABLE Employee ADD DEFAULT ((0)) FOR [changePassword]
GO
ALTER TABLE Employee ADD  DEFAULT ('TRUE') FOR [active]
GO
ALTER TABLE Employee WITH CHECK ADD CONSTRAINT [fk_employee_idCostCenter] FOREIGN KEY([idCostCenter])
REFERENCES CostCenter ([id])
GO
ALTER TABLE Employee CHECK CONSTRAINT [fk_employee_idCostCenter]
GO

ALTER TABLE CostCenter  WITH CHECK ADD CONSTRAINT [fk_costcenter_idEmployee] FOREIGN KEY([idEmployee])
REFERENCES Employee ([id])
GO
ALTER TABLE CostCenter CHECK CONSTRAINT [fk_costcenter_idEmployee]
GO

------------------------------ INTERMERDIARIA --------------------------------------------------
CREATE TABLE GR_COST_CENTER (

ID INT NOT NULL IDENTITY(1,1) ,
IDCOSTCENTER BIGINT NOT NULL,
NAME VARCHAR(70)  DEFAULT 'Centro de Custo Sem Descricao' 

CONSTRAINT pk_GR_COST_CENTER_ID PRIMARY KEY (ID)
);

GO

CREATE TABLE GR_EMPLOYEE (

ID INT NOT NULL IDENTITY(1,1),
RE VARCHAR(11) NOT NULL,
NAME VARCHAR(50) ,
EMAIL VARCHAR(150) ,
PASSWORD VARCHAR(100) ,
ACTIVE BIT,
CPF VARCHAR(11) ,
CONTRACT VARCHAR(255) ,
PHONE_NUMBER VARCHAR(15) ,
COST_CENTER BIGINT,
ROLE_GROUP VARCHAR(50) ,

CONSTRAINT pk_GR_EMPLOYEE_ID PRIMARY KEY (ID),

);

GO
