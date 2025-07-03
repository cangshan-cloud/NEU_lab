USE `neu_fund_advisory`;

INSERT INTO `fund_company` (id, company_code, company_name) VALUES
(1, 'C001', '华夏基金'),
(2, 'C002', '易方达基金'),
(3, 'C003', '华夏基金'),
(4, 'C004', '易方达基金'),
(5, 'C005', '南方基金');

INSERT INTO `fund_manager` (id, manager_code, manager_name, company_id, status, created_at, updated_at)
VALUES
(1, 'M001', '张三', 1, 'ACTIVE', NOW(), NOW()),
(2, 'M002', '李四', 2, 'ACTIVE', NOW(), NOW()),
(3, 'M003', '王五', 3, 'ACTIVE', NOW(), NOW());

INSERT INTO `factor` (id, factor_code, factor_name, factor_category, factor_type, description, status) VALUES
(1, 'F001', '成立日期', '基本面', '日期', '基金成立日期', 'active'),
(2, 'F002', '发行机构', '基本面', '字符串', '基金发行机构', 'active'),
(3, 'F003', '阶段涨跌幅', '业绩', '数值', '近一阶段涨跌幅', 'active'),
(4, 'F004', '基金规模', '规模', '数值', '基金当前规模', 'active'),
(5, 'F005', '夏普比率', '风险', '数值', '夏普比率', 'active'),
(6, 'F006', '最大回撤', '风险', '数值', '历史最大回撤', 'active'),
(7, 'F007', '波动率', '风险', '数值', '年化波动率', 'active'),
(8, 'F008', '成长风格', '风格', '数值', '成长风格因子', 'active'),
(9, 'F009', '价值风格', '风格', '数值', '价值风格因子', 'active'),
(10, 'F010', '大盘风格', '风格', '数值', '大盘风格因子', 'active'),
(11, 'COMPOSITE_1751298530787', '衍生1', '复合因子', '加权', '由多个基础因子加权生成复合因子', 'active');

INSERT INTO `factor_tree` (id, tree_name, tree_code, tree_description, status) VALUES
(11, '测试', '1', NULL, 'active');

INSERT INTO `factor_tree_node` (id, factor_id, tree_id, node_type, node_name, status) VALUES
(108, 1, 11, '因子', '成立日期', 'active'),
(109, 2, 11, '因子', '发行机构', 'active'),
(110, 3, 11, '因子', '阶段涨跌幅', 'active');

INSERT INTO `fund` (id, fund_code, company_id, manager_id, fund_name, fund_type, risk_level) VALUES
(8, '44', 1, 1, '测试基金4', 'STOCK', 'HIGH'),
(9, '11', 5, 2, '测试基金1', 'MONEY', 'MEDIUM'),
(10, '22', 3, 2, '测试基金2', 'MONEY', 'LOW'),
(11, '55', 3, 2, '测试基金5', 'HYBRID', 'MEDIUM');


INSERT INTO `fund_portfolio` (id, portfolio_name) VALUES
(3, '基金组合25'),
(4, '基金组合125'),
(5, '基金组合1245');

INSERT INTO `fund_portfolio_relation` (portfolio_id, fund_id) VALUES
(5,8),(4,9),(5,9),(3,10),(4,10),(5,10),(3,11),(4,11),(5,11);
