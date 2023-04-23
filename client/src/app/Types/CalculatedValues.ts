export interface CalculatedValues {
    requestedLoanAmount: number,
    contractFee: number,
    bankFee: number,
    mortgageRegistration: number,
    euroborAndRate: number,
    maxLoanAvailable: number,
    monthlyPayment: number,
    annualInterestRateTotal: number,
}

// {
//     requestedLoanAmount: number,
//     maxAvailableLoanAmount: number,
//     monthlyPaymentAmount: number,
//maybe? linearData?: number[]
// }