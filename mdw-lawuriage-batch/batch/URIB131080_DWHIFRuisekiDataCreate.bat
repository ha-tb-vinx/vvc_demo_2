REM @echo off
REM ============================================================================
REM タイトル：ＤＷＨIF用累積データ作成処理
REM 説明	：「ＤＷＨIF用累積データ」に、差分対象のデータを作成する。
REM version 3.0  (2013.10.09) T.Morihiro [CUS00057] ランドローム様対応 売上管理―URIB131_日別売上集計処理
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== ＤＷＨIF用累積データ作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB131080
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
