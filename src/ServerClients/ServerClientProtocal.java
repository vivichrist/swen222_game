/**
 * 
 */
package ServerClients;

/**
 * @author  Zhaojiang Chang
 *
 */
public interface ServerClientProtocal {
String userNameRep = "-1";
String loginSuccess = "1";//login success
String chatSent = "@@";//for player sent message to other players
String playerListSent = "**";//for server send playerList to all clients

}
