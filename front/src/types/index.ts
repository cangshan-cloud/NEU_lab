// 通用响应类型
export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

// 分页类型
export interface PageResult<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

// 基金公司类型
export interface FundCompany {
  id: number;
  companyCode: string;
  companyName: string;
  companyShortName?: string;
  establishmentDate?: string;
  registeredCapital?: number;
  legalRepresentative?: string;
  contactPhone?: string;
  contactEmail?: string;
  website?: string;
  address?: string;
  businessLicense?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 基金经理类型
export interface FundManager {
  id: number;
  managerCode: string;
  managerName: string;
  gender?: string;
  birthDate?: string;
  education?: string;
  experienceYears?: number;
  companyId?: number;
  introduction?: string;
  investmentPhilosophy?: string;
  awards?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 基金类型
export interface Fund {
  id: number;
  fundCode: string;
  fundName: string;
  fundType?: string;
  fundCategory?: string;
  companyId?: number;
  managerId?: number;
  inceptionDate?: string;
  fundSize?: number;
  nav?: number;
  navDate?: string;
  riskLevel?: string;
  investmentStrategy?: string;
  benchmark?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 基金标签类型
export interface FundTag {
  id: number;
  tagName: string;
  tagCategory?: string;
  tagDescription?: string;
  tagColor?: string;
  sortOrder?: number;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 基金组合类型
export interface FundPortfolio {
  id: number;
  portfolioName: string;
  portfolioCode: string;
  portfolioType?: string;
  riskLevel?: string;
  targetReturn?: number;
  maxDrawdown?: number;
  investmentHorizon?: string;
  minInvestment?: number;
  description?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 因子类型
export interface Factor {
  id: number;
  factorCode: string;
  factorName: string;
  factorCategory?: string;
  factorType?: string;
  description?: string;
  calculationFormula?: string;
  dataSource?: string;
  updateFrequency?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 因子树类型
export interface FactorTree {
  id: number;
  treeName: string;
  treeCode: string;
  treeDescription?: string;
  rootNodeId?: number;
  treeStructure?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 因子树节点类型
export interface FactorTreeNode {
  id: number;
  treeId: number;
  nodeName: string;
  nodeType?: string;
  factorId?: number;
  operator?: string;
  parentId?: number;
  nodeOrder?: number;
  weight?: number;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 因子数据类型
export interface FactorData {
  id: number;
  factorId: number;
  fundId: number;
  factorValue?: number;
  dataDate: string;
  dataSource?: string;
  dataQuality?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 策略类型
export interface Strategy {
  id: number;
  strategyCode: string;
  strategyName: string;
  strategyType?: string;
  riskLevel?: string;
  targetReturn?: number;
  maxDrawdown?: number;
  investmentHorizon?: string;
  description?: string;
  factorTreeId?: number;
  parameters?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 策略回测类型
export interface StrategyBacktest {
  id: number;
  strategyId: number;
  backtestName: string;
  startDate: string;
  endDate: string;
  initialCapital?: number;
  finalValue?: number;
  totalReturn?: number;
  annualReturn?: number;
  maxDrawdown?: number;
  sharpeRatio?: number;
  volatility?: number;
  winRate?: number;
  backtestResult?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 策略组合关联类型
export interface StrategyPortfolio {
  id: number;
  strategyId: number;
  portfolioId: number;
  weight?: number;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// FOF组合类型
export interface FofPortfolio {
  id: number;
  portfolioName: string;
  portfolioCode: string;
  portfolioType?: string;
  riskLevel?: string;
  targetReturn?: number;
  maxDrawdown?: number;
  investmentHorizon?: string;
  minInvestment?: number;
  description?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 指数组合类型
export interface IndexPortfolio {
  id: number;
  portfolioName: string;
  portfolioCode: string;
  indexCode?: string;
  indexName?: string;
  portfolioType?: string;
  riskLevel?: string;
  targetReturn?: number;
  maxDrawdown?: number;
  investmentHorizon?: string;
  minInvestment?: number;
  description?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 择时组合类型
export interface TimingPortfolio {
  id: number;
  portfolioName: string;
  portfolioCode: string;
  timingStrategy?: string;
  portfolioType?: string;
  riskLevel?: string;
  targetReturn?: number;
  maxDrawdown?: number;
  investmentHorizon?: string;
  minInvestment?: number;
  description?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 产品类型
export interface Product {
  id: number;
  productCode: string;
  productName: string;
  productType?: string;
  strategyId?: number;
  portfolioId?: number;
  riskLevel?: string;
  targetReturn?: number;
  maxDrawdown?: number;
  investmentHorizon?: string;
  minInvestment?: number;
  maxInvestment?: number;
  managementFee?: number;
  performanceFee?: number;
  subscriptionFee?: number;
  redemptionFee?: number;
  description?: string;
  prospectus?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 产品审核类型
export interface ProductReview {
  id: number;
  productId: number;
  reviewerId?: number;
  reviewType?: string;
  reviewStatus?: string;
  reviewComment?: string;
  reviewDate?: string;
  createdAt: string;
  updatedAt: string;
}

// 产品收益类型
export interface ProductPerformance {
  id: number;
  productId: number;
  performanceDate: string;
  nav?: number;
  cumulativeReturn?: number;
  dailyReturn?: number;
  weeklyReturn?: number;
  monthlyReturn?: number;
  quarterlyReturn?: number;
  yearlyReturn?: number;
  maxDrawdown?: number;
  sharpeRatio?: number;
  volatility?: number;
  status: string;
  createdAt: string;
  updatedAt: string;
}

// 交易订单类型
export interface TradeOrder {
  id: number;
  orderNo: string;
  userId: number;
  productId: number;
  tradeType: string;
  amount: number;
  shares?: number;
  status: string;
  remark?: string;
  createdAt: string;
  updatedAt: string;
  processedAt?: string;
}

// 交易记录类型
export interface TradeRecord {
  id: number;
  recordNo: string;
  orderId: number;
  userId: number;
  productId: number;
  tradeType: string;
  amount: number;
  shares?: number;
  price?: number;
  fee?: number;
  status: string;
  tradeTime: string;
  createdAt: string;
}

// 用户持仓类型
export interface UserPosition {
  id: number;
  userId: number;
  productId: number;
  shares: number;
  cost: number;
  avgCostPrice?: number;
  marketValue?: number;
  profitLoss?: number;
  profitLossRate?: number;
  updatedAt: string;
  createdAt: string;
}

// 资金流水类型
export interface CapitalFlow {
  id: number;
  flowNo: string;
  userId: number;
  flowType: string;
  amount: number;
  balanceBefore?: number;
  balanceAfter?: number;
  orderId?: number;
  productId?: number;
  status: string;
  remark?: string;
  flowTime: string;
  createdAt: string;
}

// 查询参数类型
export interface QueryParams {
  page?: number;
  size?: number;
  sort?: string;
  [key: string]: any;
}

// 用户类型
export interface User {
  id: number;
  username: string;
  email: string;
  phone?: string;
  realName?: string;
  idCard?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
} 