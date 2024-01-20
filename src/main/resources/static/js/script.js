function deleteDoctor(doctorId) {
    $.ajax({
        url: '/admin/doctor/' + doctorId,
        type: 'DELETE',
        success: function () {
            location.reload();
        },
        error: function (error) {
            console.error('Error deleting doctor:', error);
        }
    });
}


function acceptAppointment(appointmentId) {
    $.ajax({
        url: '/admin/appointment/accept/' + appointmentId,
        type: 'POST',
        success: function () {
            location.reload();
        },
        error: function (error) {
            console.error('Error deleting doctor:', error);
        }
    });
}

function deleteAppointment(appointmentId) {
    $.ajax({
        url: '/admin/appointment/delete/' + appointmentId,
        type: 'DELETE',
        success: function () {
            location.reload();
        },
        error: function (error) {
            console.error('Error deleting doctor:', error);
        }
    });
}

function validatePhoneNumber() {
    const phoneNumber = document.getElementById('phone').value;
    const phoneError = document.getElementById('phoneError');

    const isValid = /^\d{10}$/.test(phoneNumber);

    if (!isValid) {
        phoneError.innerHTML = 'Please enter a valid 10-digit phone number';
    } else {
        phoneError.innerHTML = '';
    }
}