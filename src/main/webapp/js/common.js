const SHOES_URI = '/shoes';

const reloadPage = () => {
    window.location.reload()
}

const redirect = (uri) => {
    window.location.assign(uri);
};

const logout = () => {
    fetch('/logout',
          {
              method: 'POST',
              credentials: 'include'
          })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server responded with code: ${response.status}`);
            }

            reloadPage();
        })
        .catch(error => {
            alert(`Logout is impossible: ${error}`);
        })
};
