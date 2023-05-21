/**
  2. As a user, I need an API to retrieve the friends list for an email address.
The API should receive the following JSON request:
{
email: 'andy@example.com'
}
The API should return the following JSON response on success:
{
"success": true,
"friends" :
[
'john@example.com'
],
"count" : 1
}
Please propose JSON responses for any errors that might occur.
 */
DELIMITER //
CREATE PROCEDURE GetFriendsList(IN userEmail VARCHAR(255))
BEGIN
    -- Declare variables
    DECLARE userId INT;
    -- Retrieve the user ID for the given email address
    SELECT user_id INTO userId FROM user WHERE email = userEmail;
-- Retrieve the friends list for the user
    SELECT U.email AS friend_email
    FROM friendship F
             JOIN user U ON F.friend_id = U.user_id
    WHERE F.user_id = userId
      AND F.status = 'accepted';
END //
DELIMITER ;


CALL GetFriendsList('andy@example.com');

SELECT u.email AS friend_email
FROM user u
         INNER JOIN friendship f ON u.user_id = f.user_id
         INNER JOIN user u2 ON f.user_id = u2.user_id
WHERE u2.email = 'andy@example.com'
  AND f.status = 'accepted';;
/**
  3. As a user, I need an API to retrieve the common friends list between two
email addresses.
The API should receive the following JSON request:
{
friends:
[
'andy@example.com',
'john@example.com'
]
}
The API should return the following JSON response on success:
{
"success": true,
"friends" :
[
'common@example.com'
],
"count" : 1
}
Please propose JSON responses for any errors that might occur.
 */

DELIMITER //
CREATE PROCEDURE GetCommonFriendsList(IN userEmail1 VARCHAR(255), IN userEmail2 VARCHAR(255))
BEGIN
    -- Declare variables
    DECLARE user1Id INT;
    DECLARE user2Id INT;
    -- Retrieve the user IDs for the given email addresses
    SELECT user_id INTO user1Id FROM user WHERE email = userEmail1;
    SELECT user_id INTO user2Id FROM user WHERE email = userEmail2;
    -- Retrieve the common friends list
    SELECT U.email AS friend_email
    FROM friendship F1
             JOIN friendship F2 ON F1.friend_id = F2.friend_id
             JOIN user U ON F1.friend_id = U.user_id
    WHERE F1.user_id = user1Id
      AND F2.user_id = user2Id
      AND F1.status = 'accepted'
      AND F2.status = 'accepted';
END //
DELIMITER ;

CALL GetCommonFriendsList('andy@example.com', 'john@example.com');


select u.email from friendship F1
    inner join friendship F2 ON F1.friend_id = F2.friend_id
    inner join user u on F1.friend_id = u.user_id
where F1.user_id = 1 AND
      F2.user_id = 2;

select * from friendship;