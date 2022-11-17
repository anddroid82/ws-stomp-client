# ws-stomp-client
<p>Használata:</p>
<strong>application.properties</strong>-ben lévő értékek beállítása
<ul>
<li>stompserver.url=http://localhost:8080 - a hallgató alkalmazás url-je</li>
<li>stompserver.endpoint=/api/login - a végpont, amelyhez fordul a kliens a JWT tokenért</li>
<li>stompserver.wsurl=ws://localhost:8080/api/stomp - ezen a címen kell kapcsolódni a stomp websocket-hez</li>
</ul>
<p>Használatához a Postman-ben egy POST metódust kell küldeni a http://localhost/stomp?topic=topic_címe címre (pl. http://localhost/stomp?topic=/topic/course/8), ahol még a body-ban kell beállítani a ClientUser-t:
{
    "username":"usernév",
    "password":"jelszó"
}<br>
Amennyiben az autentikáció rendben volt, akkor a kliens feliratkozott a topic-ra. Ezt követően ha a hallgató alkalmazásban a topic-ra (amire feliratkoztunk a kliensben) küldünk egy üzenetett, akkor a kliens a konzolon megjeleníti az üzenetet.
</p>
