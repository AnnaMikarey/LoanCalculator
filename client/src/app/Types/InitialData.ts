export interface InitialData {
    // lastUpdatedOn: string,
    // euribor: number,
    // bankMargin: number,
    // minInitialDeposit: number,
    // minPriceOfProperty: number,
    // maxPriceOfProperty: number,
    // defaultPriceOfProperty: number,
    // contractFee: number,
    // bankFee: number,
    // mortgageRegistrationFee: number


    euriborRate: number,
    euriborDate: string,
    bankInterestRate: number,
    annualInterestRate: number,
    contractFee: number,
    registrationFee: number,
    monthlyBankFee: number,
    maxPropertyPrice: number,
    minPropertyPrice: number,
    defaultPropertyPrice: number,
    minDepositPercent: number,
    defaultInitialDeposit: number,
    defaultMortgagePeriod: number,
    defaultSalary: number,
    defaultFinancialObligation: number
}

// {
//     euriborRate: number,
//     euriborDate: string,
//     bankInterestRate: number,
//     annualInterestRate: number,
//     contractFee: number,
//     registrationFee: number,
//     monthlyBankFee: number,
//     maxPropertyPrice: number,
//     minPropertyPrice: number,
//     defaultPropertyPrice: number,
//     minDepositPercent: number,
//     defaultInitialDeposit: number,
//     defaultMortgagePeriod: number,
//     defaultSalary: number,
//     defaultFinancialObligation: number
// }