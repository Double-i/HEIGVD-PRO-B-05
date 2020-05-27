import {Container, Row, Col, Media, NavDropdown, Button, Badge} from "react-bootstrap";
import {withRouter} from 'react-router-dom'
import * as React from "react"
import {useContext, useEffect, useState} from 'react'
import {FaCalendarCheck} from 'react-icons/fa'
import {EZT_API, sendEzApiRequest} from "../ApiHelper";
import {formatString} from "../Utils";
import {SessionContext} from "../SessionHelper";
import {notificationRedirectUrl} from "./NotificationObject";


// Endpoint to get the notification - {0} = username
const ENDPOINT_NOTIFICATION = "/users/{0}/notifications"

// Endpoint to subscribe to the server to get in "realtime" notification - {0} = username
const ENDPOINT_LIVE_NOTIFICATION = "/notifications/{0}"

// Endpoint to mark a notification as read - {0} = id of the notification
const ENDPOINT_NOTIFICATION_READ = "/notifications/{0}/markRead"

function NotificationDropdown(props) {

    const [showNotifcationsDropdown, setShowNotifcationsDropdown] = useState(false)
    const [unreadNotifications, _setUnreadNotifications] = useState([])
    const [oldNotifications, setOldNotifications] = useState([])

    // We have to use a ref to the state because it's used in a listener that is defined in useEffect
    // based on : https://medium.com/geographit/accessing-react-state-in-event-listeners-with-usestate-and-useref-hooks-8cceee73c559
    const unreadNotificationRef = React.useRef(unreadNotifications);
    const setUnreadNotifications = data => {
        unreadNotificationRef.current = data;
        _setUnreadNotifications(data);
    };

    const session = useContext(SessionContext)
    const username = session.session.getUserName()


    // The EventSource is used to receive new notification in realtime. cf. addNotification
    let eventSource;

    /**
     * This function is used to add a notification in realtime (by using EventSource - SSE).
     * @param notification
     */
    const addNotification = (notification) =>   {

        const newNotification = [...unreadNotificationRef.current]
        newNotification.push(notification)
        setUnreadNotifications(newNotification)
    }

    /**
     * We load the unread/"new" notifications
     */
    useEffect(() => {
       sendEzApiRequest(formatString(ENDPOINT_NOTIFICATION, username), 'GET')
            .then(result => {
                setUnreadNotifications(result)
            }, error => {
                console.log("Error while getting notifications : ", formatString(ENDPOINT_NOTIFICATION, username), error)
            })

        // instanciate the EventSource to get live notifications.
        eventSource = new EventSource( EZT_API + formatString(ENDPOINT_LIVE_NOTIFICATION, username),  { withCredentials: true })
        eventSource.onmessage = e => addNotification(JSON.parse(e.data))

        // Use to close the EventSource on refresh/close tab/web browser
        window.addEventListener("beforeunload", (ev) =>
        {
            eventSource.close()
        });

    }, [])


    /**
     * This function is used to redirect the user on a specific page (which is defined by the kind of notification he clicked on)
     * @param notification
     */
    const redirectToPage = (notification) => {
        const url = notificationRedirectUrl(notification)
        props.history.push(url)
    }

    /**
     * Function used has callback when the user clicked on a notification
     * It's remove the notification from the list (of the dropdown) and marked it as read
     *
     * @param notification
     */
    const notificationClicked = (notification) => {
        console.log("click notif id : ", notification)
        const newNotification = [...unreadNotifications]
        const idxNotification = newNotification.indexOf(notification)
        if (idxNotification !== -1) {
            const newOldNotification = [...oldNotifications]
            // We add the notification to old notifications and remove it from the new notifications
            newOldNotification.unshift(newNotification.splice(idxNotification, 1)[0])
            setOldNotifications(newOldNotification)
            setUnreadNotifications(newNotification)

            // We close the dropdown
            setShowNotifcationsDropdown(false)

            sendEzApiRequest(formatString(ENDPOINT_NOTIFICATION_READ, notification.id),'POST').then(
                result => {
                    console.log("Notification dropdown: notification marked as read")
                },
                error => {
                    console.log(error)
                }
            )

            redirectToPage(notification)
        }
    }
    
    return (
        <NavDropdown
            title={<span className={"nav-item"} onClick={() => {
                setShowNotifcationsDropdown(!showNotifcationsDropdown)
            }}>Notification {unreadNotifications.length > 0 &&
            <Badge variant='primary'>{unreadNotifications.length}</Badge>}</span>}

            show={showNotifcationsDropdown}

            id="basic-nav-dropdown">

            <Container className={"notification-container"}>
                <Row className={"notification-item-list"}>
                    <Col>
                        <h4>Notitifications</h4>
                        <ul className="list-unstyled">
                            {
                                unreadNotifications.map((notification, idx) => {
                                    return (<Media as="li" key={`notification-${idx}-${notification.id}`} className={"notification-item"} onClick={() => {
                                        notificationClicked(notification)
                                    }}>
                                        <FaCalendarCheck size={30}/>
                                        <Media.Body className={"notification-item-body"}>
                                            <h6>{notification.message}</h6>
                                        </Media.Body>
                                    </Media>)
                                })
                            }
                        </ul>
                    </Col>
                </Row>

                <Button variant="primary" size="lg" block
                        onClick={() => setShowNotifcationsDropdown(false)}>Fermer</Button>
            </Container>
        </NavDropdown>
    )
}

export default withRouter(NotificationDropdown)