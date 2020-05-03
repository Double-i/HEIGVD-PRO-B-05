import {Container, Row, Col, Media, NavDropdown, Button, Badge} from "react-bootstrap";
import {withRouter} from 'react-router-dom'
import * as React from "react"
import {useContext, useEffect, useState} from 'react'
import {FaCalendarCheck} from 'react-icons/fa'
import {sendEzApiRequest} from "../ApiHelper";
import {formatString} from "../Utils";
import {SessionContext} from "../SessionHelper";
import {notificationRedirectUrl} from "./NotificationObject";

const ENDPOINT_NOTIFICATION = "/users/{0}/notifications"

function NotificationDropdown(props) {
    const [showOldNotifications, setShowOldNotifications] = useState(false)
    const [showNotifcationsDropdown, setShowNotifcationsDropdown] = useState(false)
    const [unreadNotifications, setUnreadNotifications] = useState([])
    const [oldNotifications, setOldNotifications] = useState([])

    const session = useContext(SessionContext)
    const username = session.session.getUserName()

    const redirectToPage = (notification) => {
        const url = notificationRedirectUrl(notification)

        props.history.push(url)
    }
    const notificationClicked = (notification) => {
        console.log("click notif id : ", notification)

        // TODO marquer comme lu

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

            // TODO se déplacer au bonne endroit en fonction de la notification
            redirectToPage(notification)
        }
    }

    useEffect(() => {
        sendEzApiRequest(formatString(ENDPOINT_NOTIFICATION, username), 'GET')
            .then(result => {
                console.log("Just get notifications : ",result)
                setUnreadNotifications(result)
            }, error => {
                console.log("Error while getting notifications : ", formatString(ENDPOINT_NOTIFICATION, username), error)
            })
    }, [])

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
                                    return (<Media as="li" className={"notification-item"} onClick={() => {
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
                <Row className={"notification-item-list"}>
                    <Col className={"text-link"} onClick={(event) => {
                        setShowOldNotifications(!showOldNotifications)
                    }}>
                        <p>
                            {showOldNotifications ? "Afficher " : "Cacher "}
                            les anciennes notifications
                        </p>
                    </Col>
                </Row>
                {showOldNotifications &&
                <Row className={"notification-item-list"}>
                    <Col>
                        <h4>Anciennes notifications</h4>
                        <ul className="list-unstyled">
                            {
                                oldNotifications.map((notification, idx) => {
                                    return (<Media as="li" key={"oldnotification-id-" + idx}
                                                   className={"notification-item"}>
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
                }
                <Button variant="primary" size="lg" block
                        onClick={() => setShowNotifcationsDropdown(false)}>Fermer</Button>
            </Container>
        </NavDropdown>
    )
}

export default withRouter(NotificationDropdown)