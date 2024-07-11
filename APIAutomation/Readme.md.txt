
#### For Perry’s Summer Vacation Goods and Services. Created an automated test script that verifies the **Message API**. 
### As per the given requirements of Perry’s Summer Vacation Goods and Services and the JSON mentioned.

* The application should be a REST API (No need for any kind of UI)
* The application should be ready to run in the cloud or in a container.
* The application data must be persisted in a database of some type.

* The application must be able to create and get users.
	* We do not expect you do handle any kind of authentication for users.
* The application must allow users to send a message to one other user.
	* No need to consider group chats.
* The application must allow editing and deleting messages.
* The application must be able to get all the messages sent between two users.

#### APIs your developer created:
**User Model**
```
{
    "name": "first last",
    "id": "uuid-of-user"
}
```
* POST /users   
    * Create a user
* GET /users
    * list all users
* GET /users/:id
    * Get a user by id
* PUT /users/:id
    * Update a user by id
* DELETE /users/:id
    * Delete a user by id

**Message Model**
```
{
    "from": {
        "id": "fromUserId"
    },
    "to": {
        "id": "toUserId"
    },
    "message": "text content of the message",
    "id": "uuid-of-message",
    "time": "2021-03-04T00:54:30.288Z"
}
```

* POST /message  
    * Create a message
* GET /message?from=fromUserId&to=toUserId
    * Get all the messages sent between two users.
* GET /message/:id
    * Get a message by id
* PUT /message/:id
    * Update a message by id
* DELETE /message/:id
    * Delete a message by id


### Tested the **Message API** to check if it meets the requirements.

* Test Script automated using Rest Assured in Java using TestNG Framework
* Since there is no pre-existing data in the application, Initially written a createMessage() to add data 
* As we are not considering userapi, Have created two users through postman and fetched the value and assigned it as static variables
* Added the Url, endpoint as static variables and made them final
* In order to customize the execution flow used *priority* parameter in TestNG
* Since Message ID is unique and we apply in other conditions used *dependsOnMethod*, So if we don't get message id or if we throw an exception the related cases will get skipped



### Issues Identified while testing
* POST /message -- This should be changed as POST /messages in order to access the directory
* Not able to get or update messages using the path GET /message/:id , PUT /message/:id, Used with ? query parameter able to access but that is not working in PUT, Leaving an failure in automation