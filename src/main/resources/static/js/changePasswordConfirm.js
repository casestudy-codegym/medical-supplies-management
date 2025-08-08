document.addEventListener("DOMContentLoaded", function () {
    const changePasswordForm = document.querySelector("#changePasswordModal form");

    if (changePasswordForm) {
        changePasswordForm.addEventListener("submit", function (e) {
            e.preventDefault();

            Swal.fire({
                title: 'Xác nhận đổi mật khẩu?',
                text: "Sau khi đổi, bạn sẽ cần đăng nhập lại.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33'
            }).then((result) => {
                if (result.isConfirmed) {
                    changePasswordForm.submit();
                }
            });
        });
    }

    if (window.passwordChangedFlag) {
        Swal.fire({
            icon: 'success',
            title: 'Đổi mật khẩu thành công!',
            text: 'Vui lòng đăng nhập lại.',
            confirmButtonText: 'OK'
        }).then(() => {
            window.location.href = '/auth/logout';
        });
    }
});
