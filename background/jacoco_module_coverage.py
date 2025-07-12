import os
import xml.etree.ElementTree as ET
from collections import defaultdict

# 业务模块与类名映射（示例，可根据实际情况扩展/修改）
MODULE_CLASS_MAP = {
    '用户与权限': [
        'User', 'UserProfile', 'UserSegment', 'Role', 'RoleChangeRequest',
        'UserController', 'UserProfileController', 'UserSegmentController', 'RoleController', 'RoleChangeRequestController',
        'UserService', 'UserProfileService', 'UserSegmentService', 'RoleService', 'RoleChangeRequestService',
        'UserRepository', 'UserProfileRepository', 'UserSegmentRepository', 'RoleRepository', 'RoleChangeRequestRepository',
    ],
    '基金研究': [
        'Fund', 'FundCompany', 'FundManager', 'FundPortfolio', 'FundTag', 'FundPortfolioRelation', 'FundTagRelation',
        'FundController', 'FundCompanyController', 'FundManagerController', 'FundPortfolioController', 'FundTagController',
        'FundService', 'FundCompanyService', 'FundManagerService', 'FundPortfolioService', 'FundTagService',
        'FundRepository', 'FundCompanyRepository', 'FundManagerRepository', 'FundPortfolioRepository', 'FundTagRepository',
    ],
    '因子管理': [
        'Factor', 'FactorData', 'FactorTree', 'FactorTreeNode',
        'FactorController', 'FactorDataController', 'FactorTreeController', 'FactorTreeNodeController',
        'FactorService', 'FactorDataService', 'FactorTreeService', 'FactorTreeNodeService',
        'FactorRepository', 'FactorDataRepository', 'FactorTreeRepository', 'FactorTreeNodeRepository',
    ],
    '策略管理': [
        'Strategy', 'StrategyBacktest', 'StrategyPortfolio',
        'StrategyController', 'StrategyBacktestController', 'StrategyPortfolioController',
        'StrategyService', 'StrategyBacktestService', 'StrategyPortfolioService',
        'StrategyRepository', 'StrategyBacktestRepository', 'StrategyPortfolioRepository',
    ],
    '产品管理': [
        'Product', 'ProductPerformance', 'ProductReview',
        'ProductController', 'ProductPerformanceController', 'ProductReviewController',
        'ProductService', 'ProductPerformanceService', 'ProductReviewService',
        'ProductRepository', 'ProductPerformanceRepository', 'ProductReviewRepository',
    ],
    '交易': [
        'TradeOrder', 'TradeRecord', 'TradeError', 'CapitalFlow', 'DeliveryOrder', 'TimingPortfolio', 'IndexPortfolio', 'FofPortfolio',
        'TradeOrderController', 'TradeRecordController', 'TradeErrorController', 'CapitalFlowController', 'DeliveryOrderController', 'TimingPortfolioController', 'IndexPortfolioController', 'FofPortfolioController',
        'TradeOrderService', 'TradeRecordService', 'TradeErrorService', 'CapitalFlowService', 'DeliveryOrderService', 'TimingPortfolioService', 'IndexPortfolioService', 'FofPortfolioService',
        'TradeOrderRepository', 'TradeRecordRepository', 'TradeErrorRepository', 'CapitalFlowRepository', 'DeliveryOrderRepository', 'TimingPortfolioRepository', 'IndexPortfolioRepository', 'FofPortfolioRepository',
    ],
    '分析与画像': [
        'AnalyticsController', 'AnalyticsService', 'AnalyticsServiceImpl', 'UserEventLog', 'UserEventLogController', 'UserEventLogService', 'UserEventLogRepository', 'AiProfileController', 'AiProfileTaskConsumer', 'AiProfileTaskProducer', 'UserEventLogConsumer', 'UserEventLogProducer', 'SparkAiUtil',
    ],
}

# 反向映射：类名 -> 业务模块
CLASS_MODULE_MAP = {}
for module, class_list in MODULE_CLASS_MAP.items():
    for cls in class_list:
        CLASS_MODULE_MAP[cls] = module

def parse_jacoco_xml(xml_path):
    tree = ET.parse(xml_path)
    root = tree.getroot()
    class_coverage = {}
    for package in root.findall('.//package'):
        for clazz in package.findall('class'):
            class_name = clazz.get('name').split('/')[-1].split('$')[0]  # 去除包名和内部类
            missed = 0
            covered = 0
            for counter in clazz.findall('counter'):
                if counter.get('type') == 'LINE':
                    missed = int(counter.get('missed'))
                    covered = int(counter.get('covered'))
                    break
            class_coverage[class_name] = (missed, covered)
    return class_coverage

def aggregate_by_module(class_coverage):
    module_stats = defaultdict(lambda: {'missed': 0, 'covered': 0, 'classes': 0})
    for class_name, (missed, covered) in class_coverage.items():
        module = CLASS_MODULE_MAP.get(class_name)
        if module:
            module_stats[module]['missed'] += missed
            module_stats[module]['covered'] += covered
            module_stats[module]['classes'] += 1
        else:
            module_stats['未归类']['missed'] += missed
            module_stats['未归类']['covered'] += covered
            module_stats['未归类']['classes'] += 1
    return module_stats

def print_module_coverage(module_stats):
    print(f"{'业务模块':<16}{'覆盖率':>8}{'覆盖行数':>10}{'未覆盖行数':>12}{'类数':>6}")
    print('-' * 52)
    for module, stats in module_stats.items():
        total = stats['missed'] + stats['covered']
        coverage = (stats['covered'] / total * 100) if total > 0 else 0
        print(f"{module:<16}{coverage:8.2f}%{stats['covered']:10}{stats['missed']:12}{stats['classes']:6}")

def main():
    jacoco_xml = os.path.join(os.path.dirname(__file__), 'build', 'reports', 'jacoco', 'test', 'jacocoTestReport.xml')
    if not os.path.exists(jacoco_xml):
        print(f"未找到 Jacoco XML 报告: {jacoco_xml}")
        return
    class_coverage = parse_jacoco_xml(jacoco_xml)
    module_stats = aggregate_by_module(class_coverage)
    print_module_coverage(module_stats)

if __name__ == '__main__':
    main() 