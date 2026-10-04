const API = "http://localhost:8080/api";


// =====================================================
// LOGIN
// =====================================================

const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", async function(event) {

        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const message = document.getElementById("loginMessage");

        try {

            const response = await fetch(`${API}/customers/login`, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            const data = await response.json();

            if (!response.ok) {

                message.textContent =
                    data.message || "Invalid email or password.";

                return;
            }

            localStorage.setItem(
                "customer",
                JSON.stringify(data)
            );

            message.textContent =
                `Login successful! Welcome ${data.name}`;

            setTimeout(function() {
                window.location.href = "dashboard.html";
            }, 500);

        } catch (error) {

            console.error(error);

            message.textContent =
                "Unable to connect to server.";
        }
    });
}


// =====================================================
// REGISTER
// =====================================================

const registerForm = document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener("submit", async function(event) {

        event.preventDefault();

        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const phone = document.getElementById("phone").value;
        const password = document.getElementById("password").value;

        const message =
            document.getElementById("registerMessage");

        try {

            const response = await fetch(
                `${API}/customers/register`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({
                        name: name,
                        email: email,
                        phone: phone,
                        password: password
                    })
                }
            );

            const data = await response.json();

            if (!response.ok) {

                message.textContent =
                    data.message || "Registration failed.";

                return;
            }

            message.textContent =
                "Account created successfully!";

            registerForm.reset();

            setTimeout(function() {
                window.location.href = "login.html";
            }, 1500);

        } catch (error) {

            console.error(error);

            message.textContent =
                "Unable to connect to server.";
        }
    });
}


// =====================================================
// GET CUSTOMER
// =====================================================

function getCustomer() {

    const customer =
        localStorage.getItem("customer");

    if (!customer) {
        return null;
    }

    return JSON.parse(customer);
}


// =====================================================
// LOGOUT
// =====================================================

function logout() {

    localStorage.removeItem("customer");
    localStorage.removeItem("selectedServiceId");
    localStorage.removeItem("selectedBookingId");
    localStorage.removeItem("selectedInvoiceId");

    window.location.href = "login.html";
}


// =====================================================
// NAVIGATION
// =====================================================

function openVehicles() {
    window.location.href = "vehicles.html";
}

function openServices() {
    window.location.href = "services.html";
}

function openBooking() {
    window.location.href = "booking.html";
}

function openBookings() {
    window.location.href = "bookings.html";
}

function openDashboard() {
    window.location.href = "dashboard.html";
}


// =====================================================
// DASHBOARD
// =====================================================

const customerName =
    document.getElementById("customerName");

if (customerName) {

    const customer = getCustomer();

    if (customer) {

        customerName.textContent =
            customer.name;

        loadDashboardStats(customer.id);
    }
}


async function loadDashboardStats(customerId) {

    try {

        // Get vehicles
        const vehiclesResponse =
            await fetch(
                `${API}/vehicles/customer/${customerId}`
            );

        const vehicles =
            await vehiclesResponse.json();


        // Get bookings
        const bookingsResponse =
            await fetch(
                `${API}/bookings/customer/${customerId}`
            );

        const bookings =
            await bookingsResponse.json();


        // Get all invoices
        const invoicesResponse =
            await fetch(`${API}/invoices`);

        const invoices =
            await invoicesResponse.json();


        // Get all payments
        const paymentsResponse =
            await fetch(`${API}/payments`);

        const payments =
            await paymentsResponse.json();


        // Dashboard elements
        const totalVehicles =
            document.getElementById("totalVehicles");

        const totalBookings =
            document.getElementById("totalBookings");

        const pendingBookings =
            document.getElementById("pendingBookings");

        const completedBookings =
            document.getElementById("completedBookings");

        const pendingPayments =
            document.getElementById("pendingPayments");


        // Total vehicles
        if (totalVehicles) {

            totalVehicles.textContent =
                vehicles.length;
        }


        // Total bookings
        if (totalBookings) {

            totalBookings.textContent =
                bookings.length;
        }


        // Active bookings
        if (pendingBookings) {

            pendingBookings.textContent =
                bookings.filter(function(booking) {

                    return (
                        booking.status === "PENDING" ||
                        booking.status === "CONFIRMED" ||
                        booking.status === "IN_PROGRESS"
                    );

                }).length;
        }


        // Completed bookings
        if (completedBookings) {

            completedBookings.textContent =
                bookings.filter(function(booking) {

                    return booking.status === "COMPLETED";

                }).length;
        }


        // =================================================
        // PENDING PAYMENTS
        // =================================================

        if (pendingPayments) {

            // Get invoices belonging to this customer
            const customerInvoices =
                invoices.filter(function(invoice) {

                    return (
                        invoice.booking &&
                        invoice.booking.customer &&
                        invoice.booking.customer.id == customerId
                    );

                });


            // Find invoice IDs that already have payments
            const paidInvoiceIds =
                payments.map(function(payment) {

                    if (payment.invoice) {
                        return payment.invoice.id;
                    }

                    return payment.invoiceId;
                });


            // Count invoices without payment
            const pendingPaymentCount =
                customerInvoices.filter(function(invoice) {

                    return !paidInvoiceIds.includes(invoice.id);

                }).length;


            pendingPayments.textContent =
                pendingPaymentCount;
        }

    } catch (error) {

        console.error(
            "Dashboard error:",
            error
        );
    }
}


// =====================================================
// VEHICLES
// =====================================================

const vehicleForm =
    document.getElementById("vehicleForm");

if (vehicleForm) {

    const customer = getCustomer();

    if (customer) {
        loadVehicles(customer.id);
    }


    vehicleForm.addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const vehicleNumber =
                document.getElementById("vehicleNumber").value;

            const brand =
                document.getElementById("brand").value;

            const model =
                document.getElementById("model").value;

            const vehicleType =
                document.getElementById("vehicleType").value;


            try {

                const response =
                    await fetch(`${API}/vehicles`, {

                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify({

                            vehicleNumber:
                                vehicleNumber,

                            brand:
                                brand,

                            model:
                                model,

                            vehicleType:
                                vehicleType,

                            customerId:
                                customer.id
                        })
                    });


                const data =
                    await response.json();


                if (!response.ok) {

                    alert(
                        data.message ||
                        "Unable to add vehicle."
                    );

                    return;
                }


                alert(
                    "Vehicle added successfully!"
                );


                vehicleForm.reset();

                loadVehicles(customer.id);


            } catch (error) {

                console.error(error);

                alert(
                    "Unable to connect to server."
                );
            }
        }
    );
}


async function loadVehicles(customerId) {

    const vehicleList =
        document.getElementById("vehicleList");

    if (!vehicleList) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/vehicles/customer/${customerId}`
            );

        const vehicles =
            await response.json();


        vehicleList.innerHTML = "";


        if (vehicles.length === 0) {

            vehicleList.innerHTML = `
                <div class="empty-message">
                    <h3>No vehicles found</h3>
                    <p>Add your first vehicle.</p>
                </div>
            `;

            return;
        }


        vehicles.forEach(function(vehicle) {

            const card =
                document.createElement("div");

            card.className =
                "vehicle-card";


            card.innerHTML = `

                <h3>🚗 ${vehicle.vehicleNumber}</h3>

                <p>
                    <strong>Brand:</strong>
                    ${vehicle.brand}
                </p>

                <p>
                    <strong>Model:</strong>
                    ${vehicle.model}
                </p>

                <p>
                    <strong>Type:</strong>
                    ${vehicle.vehicleType}
                </p>

                <button
                    class="delete-btn"
                    onclick="deleteVehicle(${vehicle.id})">
                    Delete
                </button>
            `;


            vehicleList.appendChild(card);

        });


    } catch (error) {

        console.error(error);

        vehicleList.innerHTML = `
            <p class="error-message">
                Unable to load vehicles.
            </p>
        `;
    }
}


async function deleteVehicle(vehicleId) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this vehicle?"
        );

    if (!confirmDelete) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/vehicles/${vehicleId}`,
                {
                    method: "DELETE"
                }
            );


        const data =
            response.status !== 204
                ? await response.json()
                : null;


        if (!response.ok) {

            alert(
                data?.message ||
                "Unable to delete vehicle."
            );

            return;
        }


        alert(
            "Vehicle deleted successfully!"
        );


        const customer =
            getCustomer();


        if (customer) {
            loadVehicles(customer.id);
        }


    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to server."
        );
    }
}


// =====================================================
// SERVICES
// =====================================================

const serviceList =
    document.getElementById("serviceList");

if (serviceList) {

    loadServices();
}


async function loadServices() {

    const serviceList =
        document.getElementById("serviceList");

    if (!serviceList) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/services`
            );

        const services =
            await response.json();


        serviceList.innerHTML = "";


        if (services.length === 0) {

            serviceList.innerHTML = `
                <div class="empty-message">
                    <h3>No services available</h3>
                </div>
            `;

            return;
        }


        services.forEach(function(service) {

            const card =
                document.createElement("div");

            card.className =
                "service-card";


            card.innerHTML = `

                <div class="card-icon">
                    🔧
                </div>

                <h3>
                    ${service.name}
                </h3>

                <p>
                    ${service.description}
                </p>

                <h4>
                    ₹${service.price}
                </h4>

                <button
                    class="login-btn"
                    onclick="bookThisService(${service.id})">
                    Book Service
                </button>
            `;


            serviceList.appendChild(card);

        });


    } catch (error) {

        console.error(error);

        serviceList.innerHTML = `
            <p class="error-message">
                Unable to load services.
            </p>
        `;
    }
}


function bookThisService(serviceId) {

    localStorage.setItem(
        "selectedServiceId",
        serviceId
    );

    window.location.href =
        "booking.html";
}


// =====================================================
// BOOKING
// =====================================================

const bookingForm =
    document.getElementById("bookingForm");


if (bookingForm) {

    const customer =
        getCustomer();


    if (customer) {

        loadBookingVehicles(
            customer.id
        );

        loadBookingServices();

        loadBookingMechanics();
    }


    const selectedServiceId =
        localStorage.getItem(
            "selectedServiceId"
        );


    if (selectedServiceId) {

        setTimeout(function() {

            const serviceSelect =
                document.getElementById(
                    "serviceId"
                );


            if (serviceSelect) {

                serviceSelect.value =
                    selectedServiceId;
            }

        }, 500);
    }


    bookingForm.addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const bookingDate =
                document.getElementById(
                    "bookingDate"
                ).value;


            const bookingTime =
                document.getElementById(
                    "bookingTime"
                ).value;


            const vehicleId =
                Number(
                    document.getElementById(
                        "vehicleId"
                    ).value
                );


            const serviceId =
                Number(
                    document.getElementById(
                        "serviceId"
                    ).value
                );


            const mechanicId =
                Number(
                    document.getElementById(
                        "mechanicId"
                    ).value
                );


            try {

                const response =
                    await fetch(
                        `${API}/bookings`,
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body: JSON.stringify({

                                bookingDate:
                                    bookingDate,

                                bookingTime:
                                    bookingTime,

                                status:
                                    "PENDING",

                                customerId:
                                    customer.id,

                                vehicleId:
                                    vehicleId,

                                serviceId:
                                    serviceId,

                                mechanicId:
                                    mechanicId
                            })
                        }
                    );


                const data =
                    await response.json();


                if (!response.ok) {

                    alert(
                        data.message ||
                        "Booking failed."
                    );

                    return;
                }


                alert(
                    `Booking created successfully!
Booking ID: ${data.id}`
                );


                localStorage.removeItem(
                    "selectedServiceId"
                );


                bookingForm.reset();


                window.location.href =
                    "bookings.html";


            } catch (error) {

                console.error(error);

                alert(
                    "Unable to connect to server."
                );
            }
        }
    );
}


async function loadBookingVehicles(customerId) {

    const vehicleSelect =
        document.getElementById(
            "vehicleId"
        );


    if (!vehicleSelect) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/vehicles/customer/${customerId}`
            );


        const vehicles =
            await response.json();


        vehicleSelect.innerHTML = `
            <option value="">
                Select Vehicle
            </option>
        `;


        vehicles.forEach(function(vehicle) {

            vehicleSelect.innerHTML += `

                <option value="${vehicle.id}">
                    ${vehicle.vehicleNumber}
                    - ${vehicle.brand}
                    ${vehicle.model}
                </option>

            `;
        });


    } catch (error) {

        console.error(error);
    }
}


async function loadBookingServices() {

    const serviceSelect =
        document.getElementById(
            "serviceId"
        );


    if (!serviceSelect) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/services`
            );


        const services =
            await response.json();


        serviceSelect.innerHTML = `
            <option value="">
                Select Service
            </option>
        `;


        services.forEach(function(service) {

            serviceSelect.innerHTML += `

                <option value="${service.id}">
                    ${service.name}
                    - ₹${service.price}
                </option>

            `;
        });


    } catch (error) {

        console.error(error);
    }
}


async function loadBookingMechanics() {

    const mechanicSelect =
        document.getElementById(
            "mechanicId"
        );


    if (!mechanicSelect) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/mechanics`
            );


        const mechanics =
            await response.json();


        mechanicSelect.innerHTML = `
            <option value="">
                Select Mechanic
            </option>
        `;


        mechanics.forEach(function(mechanic) {

            mechanicSelect.innerHTML += `

                <option value="${mechanic.id}">
                    ${mechanic.name}
                    - ${mechanic.specialization}
                </option>

            `;
        });


    } catch (error) {

        console.error(error);
    }
}


// =====================================================
// BOOKINGS
// =====================================================

let allBookings = [];


const bookingList =
    document.getElementById(
        "bookingList"
    );


if (bookingList) {

    const customer =
        getCustomer();


    if (customer) {

        loadCustomerBookings(
            customer.id
        );
    }
}


async function loadCustomerBookings(customerId) {

    const bookingList =
        document.getElementById(
            "bookingList"
        );


    if (!bookingList) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/bookings/customer/${customerId}`
            );


        const bookings =
            await response.json();


        allBookings =
            bookings;


        displayBookings(
            bookings
        );


    } catch (error) {

        console.error(error);


        bookingList.innerHTML = `
            <p class="error-message">
                Unable to load bookings.
            </p>
        `;
    }
}


function filterBookings() {

    const filter =
        document.getElementById(
            "statusFilter"
        ).value;


    if (filter === "ALL") {

        displayBookings(
            allBookings
        );

        return;
    }


    const filtered =
        allBookings.filter(
            function(booking) {

                return booking.status === filter;

            }
        );


    displayBookings(
        filtered
    );
}


function displayBookings(bookings) {

    const bookingList =
        document.getElementById(
            "bookingList"
        );


    if (!bookingList) {
        return;
    }


    bookingList.innerHTML = "";


    if (bookings.length === 0) {

        bookingList.innerHTML = `
            <div class="empty-message">

                <h3>
                    No bookings found
                </h3>

                <p>
                    There are no bookings
                    with this status.
                </p>

            </div>
        `;

        return;
    }


    bookings.forEach(function(booking) {

        const card =
            document.createElement("div");


        card.className =
            "booking-card";


        const statusClass =
            booking.status.toLowerCase();


        card.innerHTML = `

            <div class="booking-header">

                <h3>
                    Booking #${booking.id}
                </h3>

                <span
                    class="status ${statusClass}">
                    ${booking.status}
                </span>

            </div>


            <div class="booking-details">

                <p>
                    🚗
                    <strong>Vehicle:</strong>
                    ${booking.vehicle?.vehicleNumber || "N/A"}
                </p>

                <p>
                    🔧
                    <strong>Service:</strong>
                    ${booking.service?.name || "N/A"}
                </p>

                <p>
                    👨‍🔧
                    <strong>Mechanic:</strong>
                    ${booking.mechanic?.name || "N/A"}
                </p>

                <p>
                    📅
                    <strong>Date:</strong>
                    ${booking.bookingDate}
                </p>

                <p>
                    🕐
                    <strong>Time:</strong>
                    ${booking.bookingTime}
                </p>

            </div>


            <div class="booking-actions">

                ${
                    booking.status === "COMPLETED"

                    ?

                    `
                    <button
                        class="login-btn"
                        onclick="openInvoice(${booking.id})">

                        View Invoice

                    </button>
                    `

                    :

                    ""
                }

            </div>
        `;


        bookingList.appendChild(
            card
        );

    });
}


// =====================================================
// INVOICE
// =====================================================

function openInvoice(bookingId) {

    localStorage.setItem(
        "selectedBookingId",
        bookingId
    );

    window.location.href =
        "invoice.html";
}


const invoiceDetails =
    document.getElementById(
        "invoiceDetails"
    );


if (invoiceDetails) {

    const bookingId =
        localStorage.getItem(
            "selectedBookingId"
        );


    if (bookingId) {

        loadInvoice(
            bookingId
        );

    } else {

        invoiceDetails.innerHTML = `
            <p class="error-message">
                No booking selected.
            </p>
        `;
    }
}


async function loadInvoice(bookingId) {

    const invoiceDetails =
        document.getElementById(
            "invoiceDetails"
        );


    if (!invoiceDetails) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/invoices/booking/${bookingId}`
            );


        const invoice =
            await response.json();


        if (!response.ok) {

            invoiceDetails.innerHTML = `

                <p class="error-message">

                    ${
                        invoice.message ||
                        "Invoice not found."
                    }

                </p>

            `;

            return;
        }


        localStorage.setItem(
            "selectedInvoiceId",
            invoice.id
        );


        invoiceDetails.innerHTML = `

            <div class="invoice-header">

                <h2>
                    🚗 AutoCare
                </h2>

                <p>
                    Invoice:
                    ${invoice.invoiceNumber}
                </p>

            </div>


            <div class="invoice-row">

                <span>
                    Service Charge
                </span>

                <span>
                    ₹${invoice.serviceCharge}
                </span>

            </div>


            <div class="invoice-row">

                <span>
                    Parts Charge
                </span>

                <span>
                    ₹${invoice.partsCharge}
                </span>

            </div>


            <div class="invoice-row">

                <span>
                    Tax
                </span>

                <span>
                    ₹${invoice.tax}
                </span>

            </div>


            <hr>


            <div class="invoice-total">

                <strong>
                    Total Amount
                </strong>

                <strong>
                    ₹${invoice.totalAmount}
                </strong>

            </div>


            <div class="invoice-payment">

                <button
                    class="login-btn"
                    onclick="openPayment()">

                    💳 Pay ₹${invoice.totalAmount}

                </button>

            </div>

        `;


    } catch (error) {

        console.error(error);


        invoiceDetails.innerHTML = `

            <p class="error-message">
                Unable to load invoice.
            </p>

        `;
    }
}


// =====================================================
// PAYMENT NAVIGATION
// =====================================================

function openPayment() {

    const invoiceId =
        localStorage.getItem(
            "selectedInvoiceId"
        );


    if (!invoiceId) {

        alert(
            "Invoice not found."
        );

        return;
    }


    window.location.href =
        "payment.html";
}


// =====================================================
// PAYMENT
// =====================================================

const paymentForm =
    document.getElementById(
        "paymentForm"
    );


if (paymentForm) {

    const invoiceId =
        localStorage.getItem(
            "selectedInvoiceId"
        );


    const paymentDate =
        document.getElementById(
            "paymentDate"
        );


    if (paymentDate) {

        const today =
            new Date()
                .toISOString()
                .split("T")[0];


        paymentDate.value =
            today;
    }


    if (invoiceId) {

        loadPaymentInvoice(
            invoiceId
        );

    } else {

        const details =
            document.getElementById(
                "paymentInvoiceDetails"
            );


        if (details) {

            details.innerHTML = `

                <p class="error-message">
                    No invoice selected.
                </p>

            `;
        }
    }


    paymentForm.addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const paymentMethod =
                document.getElementById(
                    "paymentMethod"
                ).value;


            const paymentDateValue =
                document.getElementById(
                    "paymentDate"
                ).value;


            const message =
                document.getElementById(
                    "paymentMessage"
                );


            if (!invoiceId) {

                message.innerHTML = `

                    <p class="error-message">
                        Invoice not found.
                    </p>

                `;

                return;
            }


            try {

                const invoiceResponse =
                    await fetch(
                        `${API}/invoices/${invoiceId}`
                    );


                const invoice =
                    await invoiceResponse.json();


                if (!invoiceResponse.ok) {

                    message.innerHTML = `

                        <p class="error-message">

                            ${
                                invoice.message ||
                                "Invoice not found."
                            }

                        </p>

                    `;

                    return;
                }


                const response =
                    await fetch(
                        `${API}/payments`,
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body: JSON.stringify({

                                amount:
                                    invoice.totalAmount,

                                paymentDate:
                                    paymentDateValue,

                                paymentMethod:
                                    paymentMethod,

                                status:
                                    "PAID",

                                invoiceId:
                                    Number(invoiceId)

                            })
                        }
                    );


                const data =
                    await response.json();


                if (!response.ok) {

                    message.innerHTML = `

                        <p class="error-message">

                            ${
                                data.message ||
                                "Payment failed."
                            }

                        </p>

                    `;

                    return;
                }


                message.innerHTML = `

                    <div class="success-message">

                        <h3>
                            ✅ Payment Successful!
                        </h3>

                        <p>
                            Payment ID:
                            ${data.id}
                        </p>

                        <p>
                            Amount Paid:
                            ₹${data.amount}
                        </p>

                        <p>
                            Method:
                            ${data.paymentMethod}
                        </p>

                    </div>

                `;


                paymentForm.reset();


                setTimeout(function() {

                    window.location.href =
                        "bookings.html";

                }, 2000);


            } catch (error) {

                console.error(error);


                message.innerHTML = `

                    <p class="error-message">
                        Unable to connect to server.
                    </p>

                `;
            }
        }
    );
}


async function loadPaymentInvoice(invoiceId) {

    const details =
        document.getElementById(
            "paymentInvoiceDetails"
        );


    if (!details) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/invoices/${invoiceId}`
            );


        const invoice =
            await response.json();


        if (!response.ok) {

            details.innerHTML = `

                <p class="error-message">

                    ${
                        invoice.message ||
                        "Invoice not found."
                    }

                </p>

            `;

            return;
        }


        details.innerHTML = `

            <div class="invoice-header">

                <h2>
                    AutoCare
                </h2>

                <p>
                    Invoice:
                    ${invoice.invoiceNumber}
                </p>

            </div>


            <div class="invoice-row">

                <span>
                    Service Charge
                </span>

                <span>
                    ₹${invoice.serviceCharge}
                </span>

            </div>


            <div class="invoice-row">

                <span>
                    Parts Charge
                </span>

                <span>
                    ₹${invoice.partsCharge}
                </span>

            </div>


            <div class="invoice-row">

                <span>
                    Tax
                </span>

                <span>
                    ₹${invoice.tax}
                </span>

            </div>


            <hr>


            <div class="invoice-total">

                <strong>
                    Total Amount
                </strong>

                <strong>
                    ₹${invoice.totalAmount}
                </strong>

            </div>

        `;


    } catch (error) {

        console.error(error);


        details.innerHTML = `

            <p class="error-message">
                Unable to load invoice.
            </p>

        `;
    }
}