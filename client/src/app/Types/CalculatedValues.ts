export interface CalculatedValues {
    requestedLoanAmount: number,
    maxAvailableLoanAmount: number,
    monthlyPaymentAmount: number,
    // linearData?: number[]
    totalBankFee: number,
    totalInterestAmount: number,
    totalAmountToBePaid: number
}
