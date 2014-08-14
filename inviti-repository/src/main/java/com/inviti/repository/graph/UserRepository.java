package com.inviti.repository.graph;

import com.inviti.model.Meeting;
import com.inviti.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by vladyslavprytula on 8/7/14.
 */
@Repository
public interface UserRepository extends GraphRepository<User>{
    List<User> findByUserName(String name);

    @Query("MATCH (u: User {userName:{userName}})-[know:KNOWS]->(otherUser) RETURN otherUser")
    List<User> findFriends(@Param("userName") String userName);

    @Query("MATCH (m:Meeting {meetingName:{meetingName}})<--(users) RETURN users")
    Set<User> findByMeeting(@Param("meetingName") String meetingName);

    /**
    *Searches independently of meeting, i.e. all collaborators through all the meetings that current user belongs to.
    **/
    @Query("MATCH (u:User {userName:{userName}})-[:BELONGS]->meeting<-[:BELONGS]-collaborator "+
            "RETURN collaborator")
    Set<User> findCollaborators(@Param("userName") String userName);
}
