# Loan Management System

## Scenario

A small microfinance company wants to manage customer loans. Loan officers capture loan applications, managers approve or reject them, and once approved, the system generates a repayment schedule and tracks payments until the loan is fully paid.

## Requirements

**1. Users and Security** (similar to last week, with one more role)
- Register and log in users with roles `LOAN_OFFICER`, `MANAGER`, `ADMIN`.
- Only a MANAGER can approve or reject loans. Only an ADMIN can view the audit log.
- Use Spring Security with encrypted passwords (BCrypt).

**2. Customer Management**
- Create, update, view and search customers (name, national ID, phone, email).
- National ID must be unique, and the app should reject duplicates with a clear message.

**3. Loan Application**
- A LOAN_OFFICER captures a loan for an existing customer: principal amount, interest rate, and term in months.
- Status flow: `APPLIED → APPROVED / REJECTED → ACTIVE → CLOSED`.
- **Business rules (validated and tested):**
    - A customer cannot have more than 2 active loans.
    - Principal must be within a configured min/max (put the limits in `application.properties`).
    - The officer who captured a loan cannot approve it (maker-checker).
    - A rejected loan requires a rejection reason.

**4. Repayment Schedule (the new challenge)**
- When a MANAGER approves a loan, the system automatically generates monthly installments.
- Use simple flat interest: `total = principal + (principal × rate × years)`, and `installment = total ÷ number of months`.
- Store each installment (due date, amount, status `PENDING` / `PAID` / `OVERDUE`).
- Use `BigDecimal` for all money values, **never `double`**

**5. Payments**
- Record a payment against a loan. It should clear the oldest unpaid installment first.
- When all installments are paid, the loan automatically becomes `CLOSED`.

**6. Bulk Upload**
- Upload a CSV of customers or loans.
- The system should process valid rows, skip invalid ones, and return a summary: *"48 saved, 2 failed"* with the row number and reason for each failure.

**7. Search and Pagination**
- Search loans by status, customer name or national ID, and date range.
- Results must be paginated (Spring Data `Pageable`).

**8. Reports**
- Total amount disbursed today/this month.
- Portfolio summary: number and value of loans per status.
- Overdue installments report, with a scheduled job (`@Scheduled`) that runs nightly to flag installments as `OVERDUE`.

**9. Audit Trail**
- Record who did what and when for every approval, rejection and payment (a simple `audit_log` table is enough).

**10. Quality**
- Unit tests for services (use Mockito) and at least a few controller tests (`MockMvc`).
- Share a JaCoCo coverage report with a target of at least 70% on the service layer.
- A `README` explaining how to run the project.
