create table if not exists `playerdeaths` (
	`id` uuid not null,
	`timestamp` timestamp not null,
	`playeruuid` uuid not null,
	`playername` char not null,
	`dimension` integer not null,
	`x` double not null,
	`y` double not null,
	`z` double not null,
	`type` char default null,
	`killer` char default null,
	`message` char default null
);