REM @echo off
REM ============================================================================
REM タイトル	：店別印紙税対象データ作成
REM 説明	：「レジ別取引精算データ」より、「店別印紙税対象データ」を作成する。
REM version 3.00 2013/10/15 Y.Tominaga [CUS00057] ランドローム様対応　店別印紙税対象データ作成
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 店別印紙税対象データ作成
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB041030
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
