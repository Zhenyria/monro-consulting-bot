const CONSULTATION_REQUESTS_URI = '/consultation-requests';

const deleteConsultationRequest = (id) => {
    fetch(CONSULTATION_REQUESTS_URI + '/' + id,
          {
              method: 'DELETE',
              credentials: 'include'
          })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server responded with code: ${response.status}`);
            }

            reloadPage();
        })
        .catch(error => {
            alert(`The consultation request can not be deleted: ${error}`);
        });
};
