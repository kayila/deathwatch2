create schema if not exists `deathwatch`;

create table if not exists `deaths` (
	`id` uuid,
	`timestamp` timestamp,
	`uuid` uuid,
	`player` char,
	`dimension` integer,
	`x` double,
	`y` double,
	`z` double,
	`type` char,
	`killer` char,
	`message` char
);