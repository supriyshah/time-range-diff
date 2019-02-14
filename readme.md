Assumptions:
1. Time ranges are inclusive at both ends
2. Subtraction of overlapping time ranges changes inclusion to exclusion
3. Input range is between 00:00 to 23:59. HH:MM format is preferred but H:MM argument is handled too
4. Input ranges are in ascending order i.e. 09:00 - 11:00 is valid, 11:00 - 09:00 is invalid. In this case, the values are flipped to get the time range of 09:00-11:00 for calculation
5. Spaces are ignored, - is the divider between start time and end time of a range
6. Opening "(" and closing ")" brackets are handled

Execution:
1. Recommended Approach:  If Maven installed,
	- git clone https://github.com/supriyshah/time-range-diff.git
	- Change directory to cloned repository. Run the following commands
	   mvn clean install
	   mvn exec:java


2. If Maven not installed, download the folder from GDrive and unzip. Password for unzipping is "timediff123"
   (If Embedded jar blocked by firewall, follow Step 1)
   1. Change directory to /time-range-diff/target
   2. Run "java -jar time.range.diff-1.0-SNAPSHOT.jar"