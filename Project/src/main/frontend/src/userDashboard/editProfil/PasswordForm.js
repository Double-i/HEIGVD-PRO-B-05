import * as React from 'react'
import {useState, useContext} from 'react'
import {Formik} from "formik";
import {Alert, Button, Form, Spinner} from "react-bootstrap";
import * as yup from 'yup'
import {sendEzApiRequest} from "../../common/ApiHelper";
import {SessionContext} from "../../common/SessionHelper";

function PasswordForm(props) {
    const session = useContext(SessionContext);
    const username = session.session.getUserName();
    const [savingPassword, setSavingPassword] = useState(false);
    const [hasBeenSaved, setHasBeenSaved] = useState(false)
    const [hasError, setHasError] = useState(false)

    const attemptEditPassword = values => {
        console.log("password ", values)
        setSavingPassword(false)
        sendEzApiRequest(`/users/${username}/password`,'POST', {
            currentPassword: values.currentPassword,
            newPassword : values.newPassword
        }).then(result => {
            setHasBeenSaved( true)
            setSavingPassword(false)
        }, error => {
            setHasError(true)
            setSavingPassword(false)
        })
    }

    const schema = yup.object({
        currentPassword: yup.string().required("Champ requis"),
        newPassword: yup
            .string()
            .required('Champs requis')
            .min(8, "Le mot de passe doit faire au moins 8 caractères")
            .max(32, "Le mot de passe doit faire au plus 32 caractères"),
        repeatNewPassword: yup
            .string()
            .required('Champs requis')
            .oneOf(
                [yup.ref('newPassword'), null],
                'Les mots de passe doivent être identiques'
            ),
    })

    return (
        <>
            <Alert variant="success" hidden={!hasBeenSaved}>
                Mot de passe modifié
            </Alert>
            <Alert variant="warning" hidden={!hasError}>
                Avez-vous bien tappé votre mot de passe ?
            </Alert>
            <Formik
                validationSchema={schema}
                onSubmit={(values, {setSubmitting}) => {
                    setSavingPassword(true)
                    attemptEditPassword(values)
                }}
                enableReinitialize
                initialValues={{
                    currentPassword: '',
                    newPassword: '',
                    repeatNewPassword: ''
                }}
            >
                {({
                      handleSubmit,
                      handleChange,
                      touched,
                      isValid,
                      values,
                      errors,
                  }) => (

                    <Form noValidate onSubmit={handleSubmit}>
                        <Form.Group controlId="formCurrentPassword">
                            <Form.Label>Mot de passe actuel</Form.Label>
                            <Form.Control
                                name="currentPassword"
                                type="password"
                                placeholder="Le mot de passe actuel"
                                value={values.currentPassword}
                                onChange={handleChange}
                                isValid={touched.currentPassword && !errors.currentPassword}
                                isInvalid={errors.currentPassword}
                            />
                            <Form.Control.Feedback  type="invalid">{errors.currentPassword}</Form.Control.Feedback>
                        </Form.Group>
                        <hr/>
                        <Form.Group controlId="formNewPassword">
                            <Form.Label>Nouveau mot de passe</Form.Label>
                            <Form.Control
                                name="newPassword"
                                type="password"
                                placeholder="Le nouveau mot de passe"
                                value={values.newPassword}
                                onChange={handleChange}
                                isValid={touched.newPassword && !errors.newPassword}
                                isInvalid={errors.newPassword}
                            />
                            <Form.Control.Feedback  type="invalid">{errors.newPassword}</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group controlId="formRepeatNewPassword">
                            <Form.Label>Répétez le mot de passe</Form.Label>
                            <Form.Control
                                name="repeatNewPassword"
                                type="password"
                                placeholder="Le nouveau mot de passe encore une fois"
                                value={values.repeatNewPassword}
                                onChange={handleChange}
                                isValid={touched.repeatNewPassword && !errors.repeatNewPassword}
                                isInvalid={errors.repeatNewPassword}
                            />
                            <Form.Control.Feedback  type="invalid">{errors.repeatNewPassword}</Form.Control.Feedback>
                        </Form.Group>
                        <Button
                            variant="primary"
                            type="submit"
                            size="lg"
                            block
                            disabled={savingPassword}
                        >
                            Modifier
                            <Spinner
                                as="span"
                                animation="border"
                                size="sm"
                                role="status"
                                aria-hidden="true"
                                hidden={!savingPassword}
                            />
                        </Button>
                    </Form>)
                }
            </Formik>
        </>
    )

}

export default PasswordForm