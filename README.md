<h2>Good Food (POS) System</h2>

<h4>Introduction</h4>
<p>Welcome to POS System! This software is designed to my learnimg purpose, providing a user-friendly interface and robust features for managing sales, inventory, and customer interactions.</p>

<p>
  Once the POS system is installed, follow these steps to get started:
Login: no special login.
Add Customer: add customer before place order.
Add Products: add item before place order.
place order: now you can place order.
</p>

<h4>Database</h4>
create database goodfood;
use goodfood;

create table customer(
customerId varchar(255),
customerName varchar(255),
nic varchar(13),
contact int(10),
constraint PK_Cus primary key (customerId)
);
CREATE TABLE item (
itemId VARCHAR(255),
itemName VARCHAR(255),
price DOUBLE,
qty DOUBLE,
CONSTRAINT PK_Item PRIMARY KEY (itemId)
);

CREATE TABLE orderDetail (
date DATETIME,
customerId VARCHAR(255),
orderId VARCHAR(255),
items TEXT,
total DOUBLE,
CONSTRAINT PK_Order PRIMARY KEY (orderId),
CONSTRAINT FK_Customer FOREIGN KEY (customerId) REFERENCES customer(customerId)
);


MIT License

Copyright (c) [2024] [Nipun Dhananjaya]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
