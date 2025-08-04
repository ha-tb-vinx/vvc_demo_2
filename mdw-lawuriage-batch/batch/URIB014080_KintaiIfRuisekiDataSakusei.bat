REM @echo off
REM ============================================================================
REM タイトル	：勤怠IF用累積データ作成処理
REM 説明	：勤怠IF用累積データ作成処理を実行する。
REM version 3.00 2013/10/18 T.Ooshiro:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 勤怠IF用累積データ作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB014080
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
